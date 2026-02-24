package com.poundsaver.product.kafka;

import com.poundsaver.product.service.ProductService;
import com.poundsaver.shared.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "product-scraped", groupId = "product-service-group")
    public void consumeScrapedProduct(ProductDTO productDTO) {
        log.info("Received scraped product: {} from {}", productDTO.getName(), productDTO.getRetailer());
        
        try {
            productService.saveProduct(productDTO);
            log.info("Successfully saved product: {}", productDTO.getId());
        } catch (Exception e) {
            log.error("Failed to save product: {}", e.getMessage(), e);
        }
    }
}
