package com.poundsaver.price.controller;

import com.poundsaver.price.service.PriceComparisonService;
import com.poundsaver.shared.dto.PriceComparisonDTO;
import com.poundsaver.shared.dto.PriceHistoryDTO;
import com.poundsaver.shared.enums.SortOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/prices")
@RequiredArgsConstructor
public class PriceController {

    private final PriceComparisonService priceComparisonService;

    @GetMapping("/compare")
    public Mono<ResponseEntity<PriceComparisonDTO>> compareProducts(
            @RequestParam String query,
            @RequestParam(defaultValue = "PRICE_ASC") SortOrder sortBy) {
        return priceComparisonService.compareProducts(query, sortBy)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/history/{productId}")
    public Mono<ResponseEntity<PriceHistoryDTO>> getPriceHistory(
            @PathVariable String productId,
            @RequestParam(defaultValue = "30") int days) {
        return Mono.fromCallable(() -> priceComparisonService.getPriceHistory(productId, days))
                .map(history -> history != null ? 
                        ResponseEntity.ok(history) : 
                        ResponseEntity.notFound().build());
    }
}
