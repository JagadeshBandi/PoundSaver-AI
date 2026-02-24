package com.poundsaver.price.service;

import com.poundsaver.price.entity.PriceHistory;
import com.poundsaver.price.repository.PriceHistoryRepository;
import com.poundsaver.shared.dto.PriceComparisonDTO;
import com.poundsaver.shared.dto.PriceHistoryDTO;
import com.poundsaver.shared.dto.ProductDTO;
import com.poundsaver.shared.enums.SortOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceComparisonService {

    private final WebClient.Builder webClientBuilder;
    private final PriceHistoryRepository priceHistoryRepository;

    public Mono<PriceComparisonDTO> compareProducts(String query, SortOrder sortOrder) {
        long startTime = System.currentTimeMillis();

        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/v1/products/search?query=" + query)
                .retrieve()
                .bodyToFlux(ProductDTO.class)
                .collectList()
                .map(products -> {
                    List<ProductDTO> sortedProducts = sortProducts(products, sortOrder);
                    
                    String cheapestRetailer = sortedProducts.isEmpty() ? null : 
                            sortedProducts.get(0).getRetailer();
                    
                    BigDecimal averagePrice = calculateAveragePrice(sortedProducts);
                    BigDecimal priceRange = calculatePriceRange(sortedProducts);
                    BigDecimal maxSavings = calculateMaxSavings(sortedProducts);
                    
                    long searchTimeMs = System.currentTimeMillis() - startTime;
                    
                    return PriceComparisonDTO.builder()
                            .results(sortedProducts)
                            .totalResults(sortedProducts.size())
                            .cheapestRetailer(cheapestRetailer)
                            .averagePrice(averagePrice)
                            .priceRange(priceRange)
                            .maxSavings(maxSavings)
                            .searchQuery(query)
                            .searchTimeMs(searchTimeMs)
                            .build();
                });
    }

    public PriceHistoryDTO getPriceHistory(String productId, int days) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(days);
        List<PriceHistory> history = priceHistoryRepository
                .findByProductIdAndDateRange(productId, startDate);

        if (history.isEmpty()) {
            return null;
        }

        List<PriceHistoryDTO.PricePoint> pricePoints = history.stream()
                .map(ph -> PriceHistoryDTO.PricePoint.builder()
                        .price(ph.getPrice())
                        .timestamp(ph.getTimestamp())
                        .build())
                .collect(Collectors.toList());

        BigDecimal currentPrice = history.get(0).getPrice();
        BigDecimal lowestPrice = history.stream()
                .map(PriceHistory::getPrice)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        BigDecimal highestPrice = history.stream()
                .map(PriceHistory::getPrice)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        BigDecimal averagePrice = calculateAveragePriceFromHistory(history);

        return PriceHistoryDTO.builder()
                .productId(productId)
                .retailer(history.get(0).getRetailer())
                .priceHistory(pricePoints)
                .currentPrice(currentPrice)
                .lowestPrice(lowestPrice)
                .highestPrice(highestPrice)
                .averagePrice(averagePrice)
                .build();
    }

    private List<ProductDTO> sortProducts(List<ProductDTO> products, SortOrder sortOrder) {
        Comparator<ProductDTO> comparator = switch (sortOrder) {
            case PRICE_ASC -> Comparator.comparing(ProductDTO::getPrice);
            case PRICE_DESC -> Comparator.comparing(ProductDTO::getPrice).reversed();
            case PRICE_PER_UNIT_ASC -> Comparator.comparing(ProductDTO::getPricePerUnit);
            case PRICE_PER_UNIT_DESC -> Comparator.comparing(ProductDTO::getPricePerUnit).reversed();
            case NAME_ASC -> Comparator.comparing(ProductDTO::getName);
            case NAME_DESC -> Comparator.comparing(ProductDTO::getName).reversed();
            case RETAILER_ASC -> Comparator.comparing(ProductDTO::getRetailer);
            case RETAILER_DESC -> Comparator.comparing(ProductDTO::getRetailer).reversed();
        };

        return products.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private BigDecimal calculateAveragePrice(List<ProductDTO> products) {
        if (products.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = products.stream()
                .map(ProductDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(BigDecimal.valueOf(products.size()), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculatePriceRange(List<ProductDTO> products) {
        if (products.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal min = products.stream()
                .map(ProductDTO::getPrice)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal max = products.stream()
                .map(ProductDTO::getPrice)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        return max.subtract(min);
    }

    private BigDecimal calculateMaxSavings(List<ProductDTO> products) {
        return calculatePriceRange(products);
    }

    private BigDecimal calculateAveragePriceFromHistory(List<PriceHistory> history) {
        if (history.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = history.stream()
                .map(PriceHistory::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(BigDecimal.valueOf(history.size()), 2, RoundingMode.HALF_UP);
    }
}
