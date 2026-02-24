package com.poundsaver.product.service;

import com.poundsaver.product.entity.Product;
import com.poundsaver.product.repository.ProductRepository;
import com.poundsaver.shared.dto.ProductDTO;
import com.poundsaver.shared.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product saveProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("ProductDTO cannot be null");
        }
        Product product = mapToEntity(productDTO);
        return productRepository.save(product);
    }

    @Cacheable(value = "products", key = "#id")
    public Product getProductById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + id));
    }

    @Cacheable(value = "productSearch", key = "#query")
    public List<ProductDTO> searchProducts(String query) {
        if (query == null || query.trim().isEmpty()) {
            log.warn("Empty search query provided");
            return new ArrayList<>();
        }
        
        log.info("Searching products for query: {}", query);
        
        try {
            List<Product> products = productRepository.searchProducts(query.trim());
            
            if (products == null || products.isEmpty()) {
                return new ArrayList<>();
            }
            
            return products.stream()
                    .filter(p -> p != null && p.getPrice() != null)
                    .map(this::mapToDTO)
                    .filter(dto -> dto != null)
                    .sorted(Comparator.comparing(ProductDTO::getPrice, Comparator.nullsLast(Comparator.naturalOrder())))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error searching products: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<ProductDTO> getProductsByRetailer(String retailer) {
        return productRepository.findByRetailer(retailer).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getInStockProducts(String query) {
        return productRepository.findInStockProducts(query).stream()
                .map(this::mapToDTO)
                .sorted(Comparator.comparing(ProductDTO::getPrice))
                .collect(Collectors.toList());
    }

    private Product mapToEntity(ProductDTO dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .normalizedName(dto.getNormalizedName())
                .brand(dto.getBrand())
                .category(dto.getCategory())
                .retailer(dto.getRetailer())
                .price(dto.getPrice())
                .pricePerUnit(dto.getPricePerUnit())
                .unit(dto.getUnit())
                .loyaltyPrice(dto.getLoyaltyPrice())
                .loyaltyScheme(dto.getLoyaltyScheme())
                .quantity(dto.getQuantity())
                .size(dto.getSize())
                .inStock(dto.getInStock())
                .imageUrl(dto.getImageUrl())
                .productUrl(dto.getProductUrl())
                .ean(dto.getEan())
                .scrapedAt(dto.getScrapedAt())
                .lastUpdated(dto.getLastUpdated())
                .matchConfidence(dto.getMatchConfidence())
                .build();
    }

    private ProductDTO mapToDTO(Product entity) {
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
