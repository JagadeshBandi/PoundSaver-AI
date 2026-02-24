package com.poundsaver.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("scraper-service", r -> r
                        .path("/api/v1/scraper/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("scraperCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/scraper")))
                        .uri("http://localhost:8081"))
                
                .route("product-service", r -> r
                        .path("/api/v1/products/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("productCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/product")))
                        .uri("http://localhost:8082"))
                
                .route("price-service", r -> r
                        .path("/api/v1/prices/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("priceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/price")))
                        .uri("http://localhost:8083"))
                
                .route("ai-service", r -> r
                        .path("/api/v1/ai/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("aiCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/ai")))
                        .uri("http://localhost:8084"))
                
                .route("analytics-service", r -> r
                        .path("/api/v1/analytics/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(config -> config
                                        .setName("analyticsCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/analytics")))
                        .uri("http://localhost:8085"))
                
                .build();
    }
}
