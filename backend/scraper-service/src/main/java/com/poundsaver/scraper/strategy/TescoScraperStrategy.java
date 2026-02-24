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
public class TescoScraperStrategy implements ScraperStrategy {

    private static final String TESCO_BASE_URL = "https://www.tesco.com";
    private static final String SEARCH_URL = TESCO_BASE_URL + "/groceries/en-GB/search?query=";

    @Override
    public List<ProductDTO> scrape(Page page, String searchQuery) {
        List<ProductDTO> products = new ArrayList<>();
        
        if (page == null || searchQuery == null || searchQuery.trim().isEmpty()) {
            log.warn("Invalid parameters for Tesco scraper");
            return products;
        }

        try {
            String url = SEARCH_URL + searchQuery.replace(" ", "%20");
            log.info("Navigating to Tesco: {}", url);

            page.navigate(url, new Page.NavigateOptions().setTimeout(30000));
            page.waitForLoadState();
            page.waitForTimeout(2000);

            List<ElementHandle> productElements = page.querySelectorAll("li.product-list--list-item");

            if (productElements == null || productElements.isEmpty()) {
                log.warn("No product elements found on Tesco for query: {}", searchQuery);
                return products;
            }

            log.info("Found {} product elements on Tesco", productElements.size());

            for (ElementHandle element : productElements) {
                if (element == null) {
                    continue;
                }
                try {
                    ProductDTO product = extractProductData(element);
                    if (product != null && isValidProduct(product)) {
                        products.add(product);
                    }
                } catch (Exception e) {
                    log.warn("Failed to extract product data from element: {}", e.getMessage());
                }
            }

        } catch (Exception e) {
            log.error("Error scraping Tesco: {}", e.getMessage(), e);
        }

        return products;
    }
    
    private boolean isValidProduct(ProductDTO product) {
        return product != null 
            && product.getName() != null 
            && !product.getName().isEmpty()
            && product.getPrice() != null 
            && product.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }

    private ProductDTO extractProductData(ElementHandle element) {
        if (element == null) {
            return null;
        }
        
        try {
            String name = extractText(element, "a.product-tile--title");
            String priceText = extractText(element, "p.price-per-sellable-unit span.value");
            String pricePerUnitText = extractText(element, "p.price-per-quantity-weight span.value");
            String imageUrl = extractAttribute(element, "img.product-image", "src");
            String productUrl = extractAttribute(element, "a.product-tile--title", "href");

            if (name == null || name.trim().isEmpty() || priceText == null) {
                return null;
            }

            BigDecimal price = parsePrice(priceText);
            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                return null;
            }
            
            BigDecimal pricePerUnit = parsePrice(pricePerUnitText);

            String clubcardPriceText = extractText(element, "span.offer-text");
            BigDecimal loyaltyPrice = clubcardPriceText != null ? parsePrice(clubcardPriceText) : null;

            if (pricePerUnit == null || pricePerUnit.compareTo(BigDecimal.ZERO) == 0) {
                pricePerUnit = PriceCalculator.calculatePricePerUnit(price, name, null);
            }

            return ProductDTO.builder()
                    .id(UUID.randomUUID().toString())
                    .name(name.trim())
                    .retailer("TESCO")
                    .price(price)
                    .pricePerUnit(pricePerUnit)
                    .unit(PriceCalculator.normalizeUnit("per unit"))
                    .loyaltyPrice(loyaltyPrice)
                    .loyaltyScheme(loyaltyPrice != null ? "Clubcard" : null)
                    .inStock(true)
                    .imageUrl(imageUrl != null && !imageUrl.isEmpty() ? TESCO_BASE_URL + imageUrl : null)
                    .productUrl(productUrl != null && !productUrl.isEmpty() ? TESCO_BASE_URL + productUrl : null)
                    .scrapedAt(LocalDateTime.now())
                    .lastUpdated(LocalDateTime.now())
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
