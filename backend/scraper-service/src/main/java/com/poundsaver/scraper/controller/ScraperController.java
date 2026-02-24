package com.poundsaver.scraper.controller;

import com.poundsaver.scraper.service.PlaywrightScraperService;
import com.poundsaver.shared.dto.ScrapingJobDTO;
import com.poundsaver.shared.enums.Retailer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/v1/scraper")
@RequiredArgsConstructor
public class ScraperController {

    private final PlaywrightScraperService scraperService;

    @PostMapping("/scrape")
    public CompletableFuture<ResponseEntity<?>> scrapeAll(@RequestParam String query) {
        if (query == null || query.trim().isEmpty()) {
            return CompletableFuture.completedFuture(
                ResponseEntity.badRequest()
                    .body(Map.of("error", "Query parameter cannot be empty"))
            );
        }
        
        return scraperService.scrapeAllRetailers(query.trim())
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    log.error("Error in scrapeAll: {}", ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Failed to scrape retailers", 
                                   "message", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
                });
    }

    @PostMapping("/scrape/{retailer}")
    public CompletableFuture<ResponseEntity<?>> scrapeRetailer(
            @PathVariable Retailer retailer,
            @RequestParam String query) {
        
        if (query == null || query.trim().isEmpty()) {
            return CompletableFuture.completedFuture(
                ResponseEntity.badRequest()
                    .body(Map.of("error", "Query parameter cannot be empty"))
            );
        }
        
        if (retailer == null) {
            return CompletableFuture.completedFuture(
                ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid retailer"))
            );
        }
        
        return scraperService.scrapeRetailer(retailer, query.trim())
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    log.error("Error scraping retailer {}: {}", retailer, ex.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Failed to scrape " + retailer, 
                                   "message", ex.getMessage() != null ? ex.getMessage() : "Unknown error"));
                });
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "scraper-service"));
    }
}
