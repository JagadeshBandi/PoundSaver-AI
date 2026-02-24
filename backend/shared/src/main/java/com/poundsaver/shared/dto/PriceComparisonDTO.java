package com.poundsaver.shared.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class PriceComparisonDTO {
    private List<ProductDTO> results;
    private Integer totalResults;
    private String cheapestRetailer;
    private BigDecimal averagePrice;
    private BigDecimal priceRange;
    private BigDecimal maxSavings;
    private String searchQuery;
    private Long searchTimeMs;

    // Default constructor
    public PriceComparisonDTO() {}

    // All-args constructor
    public PriceComparisonDTO(List<ProductDTO> results, Integer totalResults, String cheapestRetailer, 
                             BigDecimal averagePrice, BigDecimal priceRange, BigDecimal maxSavings, 
                             String searchQuery, Long searchTimeMs) {
        this.results = results;
        this.totalResults = totalResults;
        this.cheapestRetailer = cheapestRetailer;
        this.averagePrice = averagePrice;
        this.priceRange = priceRange;
        this.maxSavings = maxSavings;
        this.searchQuery = searchQuery;
        this.searchTimeMs = searchTimeMs;
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }

    // Getters and Setters
    public List<ProductDTO> getResults() { return results; }
    public void setResults(List<ProductDTO> results) { this.results = results; }

    public Integer getTotalResults() { return totalResults; }
    public void setTotalResults(Integer totalResults) { this.totalResults = totalResults; }

    public String getCheapestRetailer() { return cheapestRetailer; }
    public void setCheapestRetailer(String cheapestRetailer) { this.cheapestRetailer = cheapestRetailer; }

    public BigDecimal getAveragePrice() { return averagePrice; }
    public void setAveragePrice(BigDecimal averagePrice) { this.averagePrice = averagePrice; }

    public BigDecimal getPriceRange() { return priceRange; }
    public void setPriceRange(BigDecimal priceRange) { this.priceRange = priceRange; }

    public BigDecimal getMaxSavings() { return maxSavings; }
    public void setMaxSavings(BigDecimal maxSavings) { this.maxSavings = maxSavings; }

    public String getSearchQuery() { return searchQuery; }
    public void setSearchQuery(String searchQuery) { this.searchQuery = searchQuery; }

    public Long getSearchTimeMs() { return searchTimeMs; }
    public void setSearchTimeMs(Long searchTimeMs) { this.searchTimeMs = searchTimeMs; }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceComparisonDTO that = (PriceComparisonDTO) o;
        return Objects.equals(searchQuery, that.searchQuery) && Objects.equals(totalResults, that.totalResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchQuery, totalResults);
    }

    // toString
    @Override
    public String toString() {
        return "PriceComparisonDTO{" +
                "totalResults=" + totalResults +
                ", cheapestRetailer='" + cheapestRetailer + '\'' +
                ", averagePrice=" + averagePrice +
                ", maxSavings=" + maxSavings +
                '}';
    }

    // Builder pattern implementation
    public static class Builder {
        private List<ProductDTO> results;
        private Integer totalResults;
        private String cheapestRetailer;
        private BigDecimal averagePrice;
        private BigDecimal priceRange;
        private BigDecimal maxSavings;
        private String searchQuery;
        private Long searchTimeMs;

        public Builder results(List<ProductDTO> results) { this.results = results; return this; }
        public Builder totalResults(Integer totalResults) { this.totalResults = totalResults; return this; }
        public Builder cheapestRetailer(String cheapestRetailer) { this.cheapestRetailer = cheapestRetailer; return this; }
        public Builder averagePrice(BigDecimal averagePrice) { this.averagePrice = averagePrice; return this; }
        public Builder priceRange(BigDecimal priceRange) { this.priceRange = priceRange; return this; }
        public Builder maxSavings(BigDecimal maxSavings) { this.maxSavings = maxSavings; return this; }
        public Builder searchQuery(String searchQuery) { this.searchQuery = searchQuery; return this; }
        public Builder searchTimeMs(Long searchTimeMs) { this.searchTimeMs = searchTimeMs; return this; }

        public PriceComparisonDTO build() {
            return new PriceComparisonDTO(results, totalResults, cheapestRetailer, 
                averagePrice, priceRange, maxSavings, searchQuery, searchTimeMs);
        }
    }
}
