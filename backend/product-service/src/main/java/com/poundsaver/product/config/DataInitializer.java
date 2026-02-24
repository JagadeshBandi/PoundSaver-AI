package com.poundsaver.product.config;

import com.poundsaver.product.entity.Product;
import com.poundsaver.product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
@Profile("demo")
public class DataInitializer {

    @Bean
    CommandLineRunner init(ProductRepository repository) {
        return args -> {
            // Clear existing data
            repository.deleteAll();
            
            // Add sample products
            repository.save(Product.builder()
                .id("prod_001")
                .name("Organic Whole Milk")
                .normalizedName("organic whole milk")
                .brand("Yazoo")
                .category("Dairy")
                .retailer("Tesco")
                .price(new BigDecimal("1.20"))
                .pricePerUnit(new BigDecimal("0.60"))
                .unit("litre")
                .loyaltyPrice(new BigDecimal("1.10"))
                .loyaltyScheme("Clubcard")
                .quantity(1)
                .size("1 litre")
                .inStock(true)
                .imageUrl("https://images.unsplash.com/photo-1550583724-b2692b85b150?w=400&h=300&fit=crop")
                .productUrl("https://tesco.com/milk")
                .ean("0123456789012")
                .scrapedAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .matchConfidence(0.95)
                .build());

            repository.save(Product.builder()
                .id("prod_002")
                .name("Fresh White Bread")
                .normalizedName("fresh white bread")
                .brand("Hovis")
                .category("Bakery")
                .retailer("Sainsbury's")
                .price(new BigDecimal("0.80"))
                .pricePerUnit(new BigDecimal("0.40"))
                .unit("loaf")
                .loyaltyPrice(new BigDecimal("0.72"))
                .loyaltyScheme("Nectar")
                .quantity(1)
                .size("800g")
                .inStock(true)
                .imageUrl("https://images.unsplash.com/photo-1509440159596-0249088772ff?w=400&h=300&fit=crop")
                .productUrl("https://sainsburys.com/bread")
                .ean("0123456789013")
                .scrapedAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .matchConfidence(0.92)
                .build());

            repository.save(Product.builder()
                .id("prod_003")
                .name("Bananas (Fairtrade)")
                .normalizedName("bananas fairtrade")
                .brand("Fairtrade")
                .category("Fruit")
                .retailer("Asda")
                .price(new BigDecimal("0.68"))
                .pricePerUnit(new BigDecimal("1.36"))
                .unit("kg")
                .loyaltyPrice(null)
                .loyaltyScheme(null)
                .quantity(1)
                .size("500g")
                .inStock(true)
                .imageUrl("https://images.unsplash.com/photo-1566395113783-56a693d6d4e8?w=400&h=300&fit=crop")
                .productUrl("https://asda.com/bananas")
                .ean("0123456789014")
                .scrapedAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .matchConfidence(0.88)
                .build());

            repository.save(Product.builder()
                .id("prod_004")
                .name("Chicken Breast Fillets")
                .normalizedName("chicken breast fillets")
                .brand("Tesco")
                .category("Meat")
                .retailer("Tesco")
                .price(new BigDecimal("3.50"))
                .pricePerUnit(new BigDecimal("7.00"))
                .unit("kg")
                .loyaltyPrice(new BigDecimal("3.15"))
                .loyaltyScheme("Clubcard")
                .quantity(1)
                .size("500g")
                .inStock(true)
                .imageUrl("https://images.unsplash.com/photo-1587593815257-3c40958120ea?w=400&h=300&fit=crop")
                .productUrl("https://tesco.com/chicken")
                .ean("0123456789015")
                .scrapedAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .matchConfidence(0.94)
                .build());

            repository.save(Product.builder()
                .id("prod_005")
                .name("British Apples")
                .normalizedName("british apples")
                .brand("British Growers")
                .category("Fruit")
                .retailer("Tesco")
                .price(new BigDecimal("1.50"))
                .pricePerUnit(new BigDecimal("3.00"))
                .unit("kg")
                .loyaltyPrice(new BigDecimal("1.35"))
                .loyaltyScheme("Clubcard")
                .quantity(1)
                .size("500g")
                .inStock(true)
                .imageUrl("https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=400&h=300&fit=crop")
                .productUrl("https://tesco.com/apples")
                .ean("0123456789017")
                .scrapedAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .matchConfidence(0.89)
                .build());

            repository.save(Product.builder()
                .id("prod_006")
                .name("Pasta Penne")
                .normalizedName("pasta penne")
                .brand("Napolina")
                .category("Pantry")
                .retailer("Asda")
                .price(new BigDecimal("0.95"))
                .pricePerUnit(new BigDecimal("1.90"))
                .unit("kg")
                .loyaltyPrice(new BigDecimal("0.85"))
                .loyaltyScheme("Asda Rewards")
                .quantity(1)
                .size("500g")
                .inStock(true)
                .imageUrl("https://images.unsplash.com/photo-1551183053-bf91a1d81141?w=400&h=300&fit=crop")
                .productUrl("https://asda.com/pasta")
                .ean("0123456789018")
                .scrapedAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .matchConfidence(0.93)
                .build());

            repository.save(Product.builder()
                .id("prod_007")
                .name("Tomato Ketchup")
                .normalizedName("tomato ketchup")
                .brand("Heinz")
                .category("Condiments")
                .retailer("Tesco")
                .price(new BigDecimal("2.00"))
                .pricePerUnit(new BigDecimal("4.00"))
                .unit("kg")
                .loyaltyPrice(new BigDecimal("1.80"))
                .loyaltyScheme("Clubcard")
                .quantity(1)
                .size("500g")
                .inStock(true)
                .imageUrl("https://images.unsplash.com/photo-1527235519533-1e300a0b6943?w=400&h=300&fit=crop")
                .productUrl("https://tesco.com/ketchup")
                .ean("0123456789019")
                .scrapedAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .matchConfidence(0.96)
                .build());

            repository.save(Product.builder()
                .id("prod_008")
                .name("Orange Juice")
                .normalizedName("orange juice")
                .brand("Tropicana")
                .category("Drinks")
                .retailer("Sainsbury's")
                .price(new BigDecimal("1.80"))
                .pricePerUnit(new BigDecimal("1.80"))
                .unit("litre")
                .loyaltyPrice(new BigDecimal("1.62"))
                .loyaltyScheme("Nectar")
                .quantity(1)
                .size("1 litre")
                .inStock(true)
                .imageUrl("https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=400&h=300&fit=crop")
                .productUrl("https://sainsburys.com/juice")
                .ean("0123456789020")
                .scrapedAt(LocalDateTime.now())
                .lastUpdated(LocalDateTime.now())
                .matchConfidence(0.90)
                .build());

            System.out.println("Demo data loaded successfully!");
            System.out.println("Total products: " + repository.count());
        };
    }
}
