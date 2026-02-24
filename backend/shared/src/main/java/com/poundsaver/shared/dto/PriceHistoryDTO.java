package com.poundsaver.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class PriceHistoryDTO {
    private String productId;
    private String productName;
    private String retailer;
    private List<PricePoint> priceHistory;
    private BigDecimal currentPrice;
    private BigDecimal lowestPrice;
    private BigDecimal highestPrice;
    private BigDecimal averagePrice;

    // Default constructor
    public PriceHistoryDTO() {}

    // All-args constructor
    public PriceHistoryDTO(String productId, String productName, String retailer, List<PricePoint> priceHistory, 
                           BigDecimal currentPrice, BigDecimal lowestPrice, BigDecimal highestPrice, 
                           BigDecimal averagePrice) {
        this.productId = productId;
        this.productName = productName;
        this.retailer = retailer;
        this.priceHistory = priceHistory;
        this.currentPrice = currentPrice;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
        this.averagePrice = averagePrice;
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getRetailer() { return retailer; }
    public void setRetailer(String retailer) { this.retailer = retailer; }

    public List<PricePoint> getPriceHistory() { return priceHistory; }
    public void setPriceHistory(List<PricePoint> priceHistory) { this.priceHistory = priceHistory; }

    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }

    public BigDecimal getLowestPrice() { return lowestPrice; }
    public void setLowestPrice(BigDecimal lowestPrice) { this.lowestPrice = lowestPrice; }

    public BigDecimal getHighestPrice() { return highestPrice; }
    public void setHighestPrice(BigDecimal highestPrice) { this.highestPrice = highestPrice; }

    public BigDecimal getAveragePrice() { return averagePrice; }
    public void setAveragePrice(BigDecimal averagePrice) { this.averagePrice = averagePrice; }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceHistoryDTO that = (PriceHistoryDTO) o;
        return Objects.equals(productId, that.productId) && Objects.equals(retailer, that.retailer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, retailer);
    }

    // toString
    @Override
    public String toString() {
        return "PriceHistoryDTO{" +
                "productId='" + productId + '\'' +
                ", retailer='" + retailer + '\'' +
                ", currentPrice=" + currentPrice +
                ", lowestPrice=" + lowestPrice +
                '}';
    }

    // Builder pattern implementation
    public static class Builder {
        private String productId;
        private String productName;
        private String retailer;
        private List<PricePoint> priceHistory;
        private BigDecimal currentPrice;
        private BigDecimal lowestPrice;
        private BigDecimal highestPrice;
        private BigDecimal averagePrice;

        public Builder productId(String productId) { this.productId = productId; return this; }
        public Builder productName(String productName) { this.productName = productName; return this; }
        public Builder retailer(String retailer) { this.retailer = retailer; return this; }
        public Builder priceHistory(List<PricePoint> priceHistory) { this.priceHistory = priceHistory; return this; }
        public Builder currentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; return this; }
        public Builder lowestPrice(BigDecimal lowestPrice) { this.lowestPrice = lowestPrice; return this; }
        public Builder highestPrice(BigDecimal highestPrice) { this.highestPrice = highestPrice; return this; }
        public Builder averagePrice(BigDecimal averagePrice) { this.averagePrice = averagePrice; return this; }

        public PriceHistoryDTO build() {
            return new PriceHistoryDTO(productId, productName, retailer, priceHistory, 
                currentPrice, lowestPrice, highestPrice, averagePrice);
        }
    }

    // PricePoint nested class
    public static class PricePoint {
        private BigDecimal price;
        
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime timestamp;

        // Default constructor
        public PricePoint() {}

        // All-args constructor
        public PricePoint(BigDecimal price, LocalDateTime timestamp) {
            this.price = price;
            this.timestamp = timestamp;
        }

        // Static builder method
        public static Builder builder() {
            return new Builder();
        }

        // Getters and Setters
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }

        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

        // equals and hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PricePoint that = (PricePoint) o;
            return Objects.equals(price, that.price) && Objects.equals(timestamp, that.timestamp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(price, timestamp);
        }

        // toString
        @Override
        public String toString() {
            return "PricePoint{" +
                    "price=" + price +
                    ", timestamp=" + timestamp +
                    '}';
        }

        // Builder pattern implementation
        public static class Builder {
            private BigDecimal price;
            private LocalDateTime timestamp;

            public Builder price(BigDecimal price) { this.price = price; return this; }
            public Builder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

            public PricePoint build() {
                return new PricePoint(price, timestamp);
            }
        }
    }
}
