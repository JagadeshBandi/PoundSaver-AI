package com.poundsaver.scraper.strategy;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.poundsaver.shared.dto.ProductDTO;
import com.poundsaver.shared.util.PriceCalculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class LidlScraperStrategy implements ScraperStrategy {

    private static final String LIDL_BASE_URL = "https://www.lidl.co.uk";

    @Override
    public List<ProductDTO> scrape(Page page, String searchQuery) {
        List<ProductDTO> products = new ArrayList<>();

        try {
            log.info("Scraping Lidl for: {}", searchQuery);

            ProductDTO mockProduct = ProductDTO.builder()
                    .id(UUID.randomUUID().toString())
                    .name("Lidl " + searchQuery + " 1kg")
                    .retailer("LIDL")
                    .price(new BigDecimal("1.29"))
                    .pricePerUnit(new BigDecimal("1.29"))
                    .unit("per kg")
                    .inStock(true)
                    .imageUrl(LIDL_BASE_URL + "/placeholder.jpg")
                    .productUrl(LIDL_BASE_URL)
                    .scrapedAt(LocalDateTime.now())
                    .build();

            products.add(mockProduct);

        } catch (Exception e) {
            log.error("Error scraping Lidl: {}", e.getMessage(), e);
        }

        return products;
    }
}
