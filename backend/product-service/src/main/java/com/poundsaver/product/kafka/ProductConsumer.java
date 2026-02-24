package com.poundsaver.product.kafka;

import com.poundsaver.product.service.ProductService;
import com.poundsaver.shared.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {
    
    private static final Logger log = LoggerFactory.getLogger(ProductConsumer.class);

    private final ProductService productService;

    public ProductConsumer(ProductService productService) {
        this.productService = productService;
    }

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
