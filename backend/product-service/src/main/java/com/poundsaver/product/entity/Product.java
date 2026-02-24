package com.poundsaver.product.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_retailer", columnList = "retailer"),
        @Index(name = "idx_normalized_name", columnList = "normalizedName"),
        @Index(name = "idx_scraped_at", columnList = "scrapedAt")
})
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

    // Default constructor
    public Product() {}

    // All-args constructor
    public Product(String id, String name, String normalizedName, String brand, String category, 
                   String retailer, BigDecimal price, BigDecimal pricePerUnit, String unit, 
                   BigDecimal loyaltyPrice, String loyaltyScheme, Integer quantity, String size, 
                   Boolean inStock, String imageUrl, String productUrl, String ean, 
                   LocalDateTime scrapedAt, LocalDateTime lastUpdated, Double matchConfidence) {
        this.id = id;
        this.name = name;
        this.normalizedName = normalizedName;
        this.brand = brand;
        this.category = category;
        this.retailer = retailer;
        this.price = price;
        this.pricePerUnit = pricePerUnit;
        this.unit = unit;
        this.loyaltyPrice = loyaltyPrice;
        this.loyaltyScheme = loyaltyScheme;
        this.quantity = quantity;
        this.size = size;
        this.inStock = inStock;
        this.imageUrl = imageUrl;
        this.productUrl = productUrl;
        this.ean = ean;
        this.scrapedAt = scrapedAt;
        this.lastUpdated = lastUpdated;
        this.matchConfidence = matchConfidence;
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNormalizedName() { return normalizedName; }
    public void setNormalizedName(String normalizedName) { this.normalizedName = normalizedName; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getRetailer() { return retailer; }
    public void setRetailer(String retailer) { this.retailer = retailer; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getPricePerUnit() { return pricePerUnit; }
    public void setPricePerUnit(BigDecimal pricePerUnit) { this.pricePerUnit = pricePerUnit; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public BigDecimal getLoyaltyPrice() { return loyaltyPrice; }
    public void setLoyaltyPrice(BigDecimal loyaltyPrice) { this.loyaltyPrice = loyaltyPrice; }

    public String getLoyaltyScheme() { return loyaltyScheme; }
    public void setLoyaltyScheme(String loyaltyScheme) { this.loyaltyScheme = loyaltyScheme; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public Boolean getInStock() { return inStock; }
    public void setInStock(Boolean inStock) { this.inStock = inStock; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getProductUrl() { return productUrl; }
    public void setProductUrl(String productUrl) { this.productUrl = productUrl; }

    public String getEan() { return ean; }
    public void setEan(String ean) { this.ean = ean; }

    public LocalDateTime getScrapedAt() { return scrapedAt; }
    public void setScrapedAt(LocalDateTime scrapedAt) { this.scrapedAt = scrapedAt; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public Double getMatchConfidence() { return matchConfidence; }
    public void setMatchConfidence(Double matchConfidence) { this.matchConfidence = matchConfidence; }

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    // toString
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", retailer='" + retailer + '\'' +
                ", price=" + price +
                '}';
    }

    // Builder pattern implementation
    public static class Builder {
        private String id;
        private String name;
        private String normalizedName;
        private String brand;
        private String category;
        private String retailer;
        private BigDecimal price;
        private BigDecimal pricePerUnit;
        private String unit;
        private BigDecimal loyaltyPrice;
        private String loyaltyScheme;
        private Integer quantity;
        private String size;
        private Boolean inStock;
        private String imageUrl;
        private String productUrl;
        private String ean;
        private LocalDateTime scrapedAt;
        private LocalDateTime lastUpdated;
        private Double matchConfidence;

        public Builder id(String id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder normalizedName(String normalizedName) { this.normalizedName = normalizedName; return this; }
        public Builder brand(String brand) { this.brand = brand; return this; }
        public Builder category(String category) { this.category = category; return this; }
        public Builder retailer(String retailer) { this.retailer = retailer; return this; }
        public Builder price(BigDecimal price) { this.price = price; return this; }
        public Builder pricePerUnit(BigDecimal pricePerUnit) { this.pricePerUnit = pricePerUnit; return this; }
        public Builder unit(String unit) { this.unit = unit; return this; }
        public Builder loyaltyPrice(BigDecimal loyaltyPrice) { this.loyaltyPrice = loyaltyPrice; return this; }
        public Builder loyaltyScheme(String loyaltyScheme) { this.loyaltyScheme = loyaltyScheme; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder size(String size) { this.size = size; return this; }
        public Builder inStock(Boolean inStock) { this.inStock = inStock; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder productUrl(String productUrl) { this.productUrl = productUrl; return this; }
        public Builder ean(String ean) { this.ean = ean; return this; }
        public Builder scrapedAt(LocalDateTime scrapedAt) { this.scrapedAt = scrapedAt; return this; }
        public Builder lastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; return this; }
        public Builder matchConfidence(Double matchConfidence) { this.matchConfidence = matchConfidence; return this; }

        public Product build() {
            return new Product(id, name, normalizedName, brand, category, retailer, price, 
                pricePerUnit, unit, loyaltyPrice, loyaltyScheme, quantity, size, inStock, 
                imageUrl, productUrl, ean, scrapedAt, lastUpdated, matchConfidence);
        }
    }
}
