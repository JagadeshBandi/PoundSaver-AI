package com.poundsaver.product.controller;

import com.poundsaver.product.service.ProductService;
import com.poundsaver.shared.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/search")
    public Mono<ResponseEntity<List<ProductDTO>>> searchProducts(@RequestParam String query) {
        return Mono.fromCallable(() -> productService.searchProducts(query))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/in-stock")
    public Mono<ResponseEntity<List<ProductDTO>>> getInStockProducts(@RequestParam String query) {
        return Mono.fromCallable(() -> productService.getInStockProducts(query))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/retailer/{retailer}")
    public Mono<ResponseEntity<List<ProductDTO>>> getProductsByRetailer(@PathVariable String retailer) {
        return Mono.fromCallable(() -> productService.getProductsByRetailer(retailer))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDTO>> getProductById(@PathVariable String id) {
        return Mono.fromCallable(() -> productService.getProductById(id))
                .map(product -> ResponseEntity.ok(mapToDTO(product)))
                .onErrorReturn(ResponseEntity.notFound().build());
    }

    private ProductDTO mapToDTO(com.poundsaver.product.entity.Product entity) {
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .normalizedName(entity.getNormalizedName())
                .brand(entity.getBrand())
                .category(entity.getCategory())
                .retailer(entity.getRetailer())
                .price(entity.getPrice())
                .pricePerUnit(entity.getPricePerUnit())
                .unit(entity.getUnit())
                .loyaltyPrice(entity.getLoyaltyPrice())
                .loyaltyScheme(entity.getLoyaltyScheme())
                .quantity(entity.getQuantity())
                .size(entity.getSize())
                .inStock(entity.getInStock())
                .imageUrl(entity.getImageUrl())
                .productUrl(entity.getProductUrl())
                .ean(entity.getEan())
                .scrapedAt(entity.getScrapedAt())
                .lastUpdated(entity.getLastUpdated())
                .matchConfidence(entity.getMatchConfidence())
                .build();
    }
}
