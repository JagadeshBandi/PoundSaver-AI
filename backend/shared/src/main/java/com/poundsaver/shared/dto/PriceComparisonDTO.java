package com.poundsaver.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceComparisonDTO {
    private List<ProductDTO> results;
    private Integer totalResults;
    private String cheapestRetailer;
    private BigDecimal averagePrice;
    private BigDecimal priceRange;
    private BigDecimal maxSavings;
    private String searchQuery;
    private Long searchTimeMs;
}
