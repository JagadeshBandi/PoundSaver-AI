package com.poundsaver.scraper.strategy;

import com.microsoft.playwright.Page;
import com.poundsaver.shared.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class BMScraperStrategy implements ScraperStrategy {

    private static final String BM_BASE_URL = "https://www.bmstores.co.uk";

    @Override
    public List<ProductDTO> scrape(Page page, String searchQuery) {
        List<ProductDTO> products = new ArrayList<>();

        try {
            log.info("Scraping B&M for: {}", searchQuery);

            ProductDTO mockProduct = ProductDTO.builder()
                    .id(UUID.randomUUID().toString())
                    .name("B&M " + searchQuery + " 500g")
                    .retailer("BM")
                    .price(new BigDecimal("0.99"))
                    .pricePerUnit(new BigDecimal("1.98"))
                    .unit("per kg")
                    .inStock(true)
                    .imageUrl(BM_BASE_URL + "/placeholder.jpg")
                    .productUrl(BM_BASE_URL)
                    .scrapedAt(LocalDateTime.now())
                    .build();

            products.add(mockProduct);

        } catch (Exception e) {
            log.error("Error scraping B&M: {}", e.getMessage(), e);
        }

        return products;
    }
}
