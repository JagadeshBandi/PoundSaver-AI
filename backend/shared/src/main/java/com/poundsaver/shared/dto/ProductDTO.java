package com.poundsaver.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class ProductDTO {
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
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime scrapedAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdated;
    
    private Double matchConfidence;

    // Default constructor
    public ProductDTO() {}

    // All-args constructor
    public ProductDTO(String id, String name, String normalizedName, String brand, String category, 
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

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    // toString
    @Override
    public String toString() {
        return "ProductDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", retailer='" + retailer + '\'' +
                ", price=" + price +
                '}';
    }
}
