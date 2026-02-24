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
public class AsdaScraperStrategy implements ScraperStrategy {

    private static final String ASDA_BASE_URL = "https://groceries.asda.com";
    private static final String SEARCH_URL = ASDA_BASE_URL + "/search/";

    @Override
    public List<ProductDTO> scrape(Page page, String searchQuery) {
        List<ProductDTO> products = new ArrayList<>();

        try {
            String url = SEARCH_URL + searchQuery.replace(" ", "%20");
            log.info("Navigating to Asda: {}", url);

            page.navigate(url);
            page.waitForLoadState();
            page.waitForTimeout(2000);

            List<ElementHandle> productElements = page.querySelectorAll("div.co-product");

            log.info("Found {} product elements on Asda", productElements.size());

            for (ElementHandle element : productElements) {
                try {
                    ProductDTO product = extractProductData(element);
                    if (product != null) {
                        products.add(product);
                    }
                } catch (Exception e) {
                    log.warn("Failed to extract product data from element: {}", e.getMessage());
                }
            }

        } catch (Exception e) {
            log.error("Error scraping Asda: {}", e.getMessage(), e);
        }

        return products;
    }

    private ProductDTO extractProductData(ElementHandle element) {
        try {
            String name = extractText(element, "a.co-product__anchor");
            String priceText = extractText(element, "strong.co-product__price");
            String imageUrl = extractAttribute(element, "img.co-product__image", "src");
            String productUrl = extractAttribute(element, "a.co-product__anchor", "href");

            if (name == null || priceText == null) {
                return null;
            }

            BigDecimal price = parsePrice(priceText);
            BigDecimal pricePerUnit = PriceCalculator.calculatePricePerUnit(price, name, null);

            return ProductDTO.builder()
                    .id(UUID.randomUUID().toString())
                    .name(name)
                    .retailer("ASDA")
                    .price(price)
                    .pricePerUnit(pricePerUnit)
                    .unit(PriceCalculator.normalizeUnit("per unit"))
                    .inStock(true)
                    .imageUrl(imageUrl)
                    .productUrl(productUrl != null ? ASDA_BASE_URL + productUrl : null)
                    .scrapedAt(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            log.warn("Error extracting product data: {}", e.getMessage());
            return null;
        }
    }

    private String extractText(ElementHandle parent, String selector) {
        try {
            ElementHandle element = parent.querySelector(selector);
            return element != null ? element.textContent().trim() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String extractAttribute(ElementHandle parent, String selector, String attribute) {
        try {
            ElementHandle element = parent.querySelector(selector);
            return element != null ? element.getAttribute(attribute) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private BigDecimal parsePrice(String priceText) {
        if (priceText == null || priceText.isEmpty()) {
            return null;
        }

        try {
            String cleaned = priceText.replaceAll("[^0-9.]", "");
            return new BigDecimal(cleaned);
        } catch (NumberFormatException e) {
            log.warn("Failed to parse price: {}", priceText);
            return null;
        }
    }
}
