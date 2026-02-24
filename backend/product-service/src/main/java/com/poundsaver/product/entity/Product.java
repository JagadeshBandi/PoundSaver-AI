package com.poundsaver.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_retailer", columnList = "retailer"),
        @Index(name = "idx_normalized_name", columnList = "normalizedName"),
        @Index(name = "idx_scraped_at", columnList = "scrapedAt")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;

    @Column(nullable = false, length = 500)
    private String name;

    @Column(length = 500)
    private String normalizedName;

    @Column(length = 100)
    private String brand;

    @Column(length = 100)
    private String category;

    @Column(nullable = false, length = 50)
    private String retailer;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(precision = 10, scale = 4)
    private BigDecimal pricePerUnit;

    @Column(length = 50)
    private String unit;

    @Column(precision = 10, scale = 2)
    private BigDecimal loyaltyPrice;

    @Column(length = 50)
    private String loyaltyScheme;

    private Integer quantity;

    @Column(length = 100)
    private String size;

    @Column(nullable = false)
    private Boolean inStock;

    @Column(length = 500)
    private String imageUrl;

    @Column(length = 500)
    private String productUrl;

    @Column(length = 50)
    private String ean;

    @Column(nullable = false)
    private LocalDateTime scrapedAt;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    private Double matchConfidence;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}
