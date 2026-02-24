package com.poundsaver.scraper.service;

import com.microsoft.playwright.*;
import com.poundsaver.scraper.strategy.ScraperStrategy;
import com.poundsaver.scraper.strategy.ScraperStrategyFactory;
import com.poundsaver.shared.dto.ProductDTO;
import com.poundsaver.shared.dto.ScrapingJobDTO;
import com.poundsaver.shared.enums.JobStatus;
import com.poundsaver.shared.enums.Retailer;
import com.poundsaver.shared.exception.ScrapingException;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class PlaywrightScraperService {

    private final KafkaTemplate<String, ProductDTO> kafkaTemplate;
    private final ScraperStrategyFactory strategyFactory;
    private final MeterRegistry meterRegistry;
    private final Counter scraperRequestsCounter;
    private final Timer scraperTimer;

    public PlaywrightScraperService(
            KafkaTemplate<String, ProductDTO> kafkaTemplate,
            ScraperStrategyFactory strategyFactory,
            MeterRegistry meterRegistry) {
        this.kafkaTemplate = kafkaTemplate;
        this.strategyFactory = strategyFactory;
        this.meterRegistry = meterRegistry;
        this.scraperRequestsCounter = Counter.builder("scraper_requests_total")
                .description("Total scraping requests")
                .register(meterRegistry);
        this.scraperTimer = Timer.builder("scraper_duration_seconds")
                .description("Scraping duration")
                .register(meterRegistry);
    }

    public CompletableFuture<ScrapingJobDTO> scrapeRetailer(Retailer retailer, String searchQuery) {
        return CompletableFuture.supplyAsync(() -> {
            if (retailer == null) {
                throw new IllegalArgumentException("Retailer cannot be null");
            }
            
            if (searchQuery == null || searchQuery.trim().isEmpty()) {
                throw new IllegalArgumentException("Search query cannot be null or empty");
            }
            
            String jobId = UUID.randomUUID().toString();
            ScrapingJobDTO job = ScrapingJobDTO.builder()
                    .jobId(jobId)
                    .retailer(retailer)
                    .searchQuery(searchQuery.trim())
                    .status(JobStatus.IN_PROGRESS)
                    .startedAt(LocalDateTime.now())
                    .build();

            scraperRequestsCounter.increment();

            return scraperTimer.record(() -> {
                Playwright playwright = null;
                Browser browser = null;
                BrowserContext context = null;
                
                try {
                    playwright = Playwright.create();
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                            .setHeadless(true)
                            .setArgs(List.of("--disable-blink-features=AutomationControlled")));

                    context = browser.newContext(new Browser.NewContextOptions()
                            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"));

                    Page page = context.newPage();

                    ScraperStrategy strategy = strategyFactory.getStrategy(retailer);
                    if (strategy == null) {
                        throw new ScrapingException("No scraper strategy found for retailer: " + retailer);
                    }
                    
                    List<ProductDTO> products = strategy.scrape(page, searchQuery.trim());

                    if (products != null && !products.isEmpty()) {
                        products.forEach(product -> {
                            try {
                                if (product != null) {
                                    kafkaTemplate.send("product-scraped", product);
                                }
                            } catch (Exception e) {
                                log.error("Failed to send product to Kafka: {}", e.getMessage());
                            }
                        });
                    }

                    job.setStatus(JobStatus.COMPLETED);
                    job.setProductsScraped(products != null ? products.size() : 0);
                    job.setCompletedAt(LocalDateTime.now());
                    job.setDurationMs(
                            java.time.Duration.between(job.getStartedAt(), job.getCompletedAt()).toMillis()
                    );

                    log.info("Scraping job {} completed. Scraped {} products from {}",
                            jobId, products != null ? products.size() : 0, retailer);

                    return job;

                } catch (Exception e) {
                    log.error("Scraping job {} failed for retailer {}: {}",
                            jobId, retailer, e.getMessage(), e);
                    
                    job.setStatus(JobStatus.FAILED);
                    job.setErrorMessage(e.getMessage() != null ? e.getMessage() : "Unknown error");
                    job.setCompletedAt(LocalDateTime.now());
                    job.setDurationMs(
                            java.time.Duration.between(job.getStartedAt(), job.getCompletedAt()).toMillis()
                    );
                    
                    return job;
                    
                } finally {
                    try {
                        if (context != null) {
                            context.close();
                        }
                    } catch (Exception e) {
                        log.warn("Error closing browser context: {}", e.getMessage());
                    }
                    
                    try {
                        if (browser != null) {
                            browser.close();
                        }
                    } catch (Exception e) {
                        log.warn("Error closing browser: {}", e.getMessage());
                    }
                    
                    try {
                        if (playwright != null) {
                            playwright.close();
                        }
                    } catch (Exception e) {
                        log.warn("Error closing playwright: {}", e.getMessage());
                    }
                }
            });
        });
    }

    public CompletableFuture<List<ScrapingJobDTO>> scrapeAllRetailers(String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return CompletableFuture.completedFuture(new ArrayList<>());
        }
        
        List<CompletableFuture<ScrapingJobDTO>> futures = new ArrayList<>();

        for (Retailer retailer : Retailer.values()) {
            try {
                futures.add(scrapeRetailer(retailer, searchQuery));
            } catch (Exception e) {
                log.error("Failed to initiate scraping for retailer {}: {}", retailer, e.getMessage());
            }
        }

        if (futures.isEmpty()) {
            return CompletableFuture.completedFuture(new ArrayList<>());
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream()
                        .map(future -> {
                            try {
                                return future.join();
                            } catch (Exception e) {
                                log.error("Error joining scraping future: {}", e.getMessage());
                                return null;
                            }
                        })
                        .filter(job -> job != null)
                        .toList())
                .exceptionally(ex -> {
                    log.error("Error in scrapeAllRetailers: {}", ex.getMessage());
                    return new ArrayList<>();
                });
    }
}
