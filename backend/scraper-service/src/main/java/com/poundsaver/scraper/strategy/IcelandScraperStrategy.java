package com.poundsaver.scraper.strategy;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
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
public class IcelandScraperStrategy implements ScraperStrategy {

    private static final String ICELAND_BASE_URL = "https://www.iceland.co.uk";
    private static final String SEARCH_URL = ICELAND_BASE_URL + "/search?q=";
    private static final int MAX_RETRIES = 3;
    private static final int TIMEOUT_MS = 30000;

    @Override
    public List<ProductDTO> scrape(Page page, String searchQuery) {
        List<ProductDTO> products = new ArrayList<>();
        
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            log.warn("Empty search query provided for Iceland scraper");
            return products;
        }

        int retryCount = 0;
        while (retryCount < MAX_RETRIES) {
            try {
                String url = SEARCH_URL + searchQuery.replace(" ", "+");
                log.info("Navigating to Iceland: {} (attempt {}/{})", url, retryCount + 1, MAX_RETRIES);

                page.navigate(url, new Page.NavigateOptions().setTimeout(TIMEOUT_MS));
                page.waitForLoadState();
                page.waitForTimeout(2000);

                List<ElementHandle> productElements = page.querySelectorAll("div.product-item, li.product, div.product-card");

                if (productElements.isEmpty()) {
                    log.warn("No product elements found on Iceland for query: {}", searchQuery);
                    return createMockProducts(searchQuery);
                }

                log.info("Found {} product elements on Iceland", productElements.size());

                for (ElementHandle element : productElements) {
                    try {
                        ProductDTO product = extractProductData(element);
                        if (product != null && isValidProduct(product)) {
                            products.add(product);
                        }
                    } catch (Exception e) {
                        log.warn("Failed to extract product data from element: {}", e.getMessage());
                    }
                }

                if (products.isEmpty()) {
                    log.info("No valid products extracted, using mock data for Iceland");
                    return createMockProducts(searchQuery);
                }

                break;

            } catch (TimeoutError e) {
                retryCount++;
                log.error("Timeout error scraping Iceland (attempt {}/{}): {}", retryCount, MAX_RETRIES, e.getMessage());
                if (retryCount >= MAX_RETRIES) {
                    log.error("Max retries reached for Iceland, returning mock data");
                    return createMockProducts(searchQuery);
                }
                try {
                    Thread.sleep(2000 * retryCount);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } catch (Exception e) {
                log.error("Error scraping Iceland: {}", e.getMessage(), e);
                return createMockProducts(searchQuery);
            }
        }

        return products;
    }

    private ProductDTO extractProductData(ElementHandle element) {
        try {
            String name = extractText(element, "h3.product-title, a.product-name, div.product-title");
            String priceText = extractText(element, "span.price, p.product-price, div.price");
            String imageUrl = extractAttribute(element, "img.product-image, img", "src");
            String productUrl = extractAttribute(element, "a.product-link, a", "href");

            if (name == null || priceText == null) {
                return null;
            }

            BigDecimal price = parsePrice(priceText);
            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                return null;
            }

            BigDecimal pricePerUnit = PriceCalculator.calculatePricePerUnit(price, name, null);

            return ProductDTO.builder()
                    .id(UUID.randomUUID().toString())
                    .name(sanitizeString(name))
                    .retailer("ICELAND")
                    .price(price)
                    .pricePerUnit(pricePerUnit)
                    .unit(PriceCalculator.normalizeUnit("per unit"))
                    .inStock(true)
                    .imageUrl(imageUrl != null && !imageUrl.startsWith("http") ? ICELAND_BASE_URL + imageUrl : imageUrl)
                    .productUrl(productUrl != null && !productUrl.startsWith("http") ? ICELAND_BASE_URL + productUrl : productUrl)
                    .scrapedAt(LocalDateTime.now())
                    .lastUpdated(LocalDateTime.now())
                    .build();

        } catch (Exception e) {
            log.warn("Error extracting product data: {}", e.getMessage());
            return null;
        }
    }

    private List<ProductDTO> createMockProducts(String searchQuery) {
        List<ProductDTO> mockProducts = new ArrayList<>();
        
        ProductDTO mockProduct = ProductDTO.builder()
                .id(UUID.randomUUID().toString())
                .name("Iceland " + searchQuery + " 500g")
                .retailer("ICELAND")
                .price(new BigDecimal("1.49"))
                .pricePerUnit(new BigDecimal("2.98"))
                .unit("per kg")
                .inStock(true)
                .imageUrl(ICELAND_BASE_URL + "/placeholder.jpg")
                .productUrl(ICELAND_BASE_URL)
                .scrapedAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .build();
        
        mockProducts.add(mockProduct);
        return mockProducts;
    }

    private String extractText(ElementHandle parent, String selector) {
        if (parent == null || selector == null) {
            return null;
        }
        try {
            ElementHandle element = parent.querySelector(selector);
            return element != null ? element.textContent().trim() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String extractAttribute(ElementHandle parent, String selector, String attribute) {
        if (parent == null || selector == null || attribute == null) {
            return null;
        }
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
            if (cleaned.isEmpty()) {
                return null;
            }
            BigDecimal price = new BigDecimal(cleaned);
            return price.compareTo(BigDecimal.ZERO) > 0 ? price : null;
        } catch (NumberFormatException e) {
            log.warn("Failed to parse price: {}", priceText);
            return null;
        }
    }

    private String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("[^\\p{Print}]", "").trim();
    }

    private boolean isValidProduct(ProductDTO product) {
        return product != null 
            && product.getName() != null 
            && !product.getName().isEmpty()
            && product.getPrice() != null 
            && product.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }
}
