package com.poundsaver.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/scraper")
    public ResponseEntity<Map<String, String>> scraperFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "error", "Scraper service is currently unavailable",
                        "message", "Please try again later"
                ));
    }

    @GetMapping("/product")
    public ResponseEntity<Map<String, String>> productFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "error", "Product service is currently unavailable",
                        "message", "Please try again later"
                ));
    }

    @GetMapping("/price")
    public ResponseEntity<Map<String, String>> priceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "error", "Price service is currently unavailable",
                        "message", "Please try again later"
                ));
    }

    @GetMapping("/ai")
    public ResponseEntity<Map<String, String>> aiFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "error", "AI service is currently unavailable",
                        "message", "Please try again later"
                ));
    }

    @GetMapping("/analytics")
    public ResponseEntity<Map<String, String>> analyticsFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "error", "Analytics service is currently unavailable",
                        "message", "Please try again later"
                ));
    }
}
