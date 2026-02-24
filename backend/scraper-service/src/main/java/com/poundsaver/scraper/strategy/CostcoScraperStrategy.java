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
public class CostcoScraperStrategy implements ScraperStrategy {

    private static final String COSTCO_BASE_URL = "https://www.costco.co.uk";

    @Override
    public List<ProductDTO> scrape(Page page, String searchQuery) {
        List<ProductDTO> products = new ArrayList<>();

        try {
            log.info("Scraping Costco for: {}", searchQuery);

            ProductDTO mockProduct = ProductDTO.builder()
                    .id(UUID.randomUUID().toString())
                    .name("Costco " + searchQuery + " Bulk Pack 5kg")
                    .retailer("COSTCO")
                    .price(new BigDecimal("8.99"))
                    .pricePerUnit(new BigDecimal("1.80"))
                    .unit("per kg")
                    .quantity(5)
                    .inStock(true)
                    .imageUrl(COSTCO_BASE_URL + "/placeholder.jpg")
                    .productUrl(COSTCO_BASE_URL)
                    .scrapedAt(LocalDateTime.now())
                    .build();

            products.add(mockProduct);

        } catch (Exception e) {
            log.error("Error scraping Costco: {}", e.getMessage(), e);
        }

        return products;
    }
}
