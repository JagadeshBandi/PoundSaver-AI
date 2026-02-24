package com.poundsaver.ai.controller;

import com.poundsaver.ai.service.ProductMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/ai")
@RequiredArgsConstructor
public class AiController {

    private final ProductMatchingService productMatchingService;

    @PostMapping("/normalize")
    public ResponseEntity<Map<String, String>> normalizeProductName(@RequestBody Map<String, String> request) {
        String productName = request.get("productName");
        String normalized = productMatchingService.normalizeProductName(productName);
        return ResponseEntity.ok(Map.of("normalized", normalized));
    }

    @PostMapping("/extract-brand")
    public ResponseEntity<Map<String, String>> extractBrand(@RequestBody Map<String, String> request) {
        String productName = request.get("productName");
        String brand = productMatchingService.extractBrand(productName);
        return ResponseEntity.ok(Map.of("brand", brand));
    }

    @PostMapping("/match-confidence")
    public ResponseEntity<Map<String, Double>> calculateMatchConfidence(@RequestBody Map<String, String> request) {
        String product1 = request.get("product1");
        String product2 = request.get("product2");
        double confidence = productMatchingService.calculateMatchConfidence(product1, product2);
        return ResponseEntity.ok(Map.of("confidence", confidence));
    }
}
