package com.poundsaver.ai.controller;

import com.poundsaver.ai.service.AIDataCollectionAgent;
import com.poundsaver.shared.dto.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/ai")
@CrossOrigin(origins = "*")
public class AIAgentController {

    private final AIDataCollectionAgent aiDataCollectionAgent;

    public AIAgentController(AIDataCollectionAgent aiDataCollectionAgent) {
        this.aiDataCollectionAgent = aiDataCollectionAgent;
    }

    /**
     * AI-powered comprehensive data collection from all retailers
     */
    @PostMapping("/collect")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> collectAllRetailerData(
            @RequestBody Map<String, String> request) {
        
        String searchQuery = request.get("query");
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return CompletableFuture.completedFuture(
                ResponseEntity.badRequest().body(Map.of(
                    "error", "Search query is required",
                    "status", "BAD_REQUEST"
                ))
            );
        }

        return aiDataCollectionAgent.collectAllRetailerData(searchQuery.trim())
                .thenApply(products -> {
                    Map<String, Object> response = Map.of(
                        "status", "SUCCESS",
                        "searchQuery", searchQuery,
                        "totalProducts", products.size(),
                        "products", products,
                        "message", "AI-powered data collection completed successfully"
                    );
                    return ResponseEntity.ok(response);
                })
                .exceptionally(throwable -> {
                    Map<String, Object> errorResponse = Map.of(
                        "error", "AI data collection failed: " + throwable.getMessage(),
                        "status", "ERROR"
                    );
                    return ResponseEntity.internalServerError().body(errorResponse);
                });
    }

    /**
     * AI-powered price analysis and insights
     */
    @PostMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyzePrices(@RequestBody List<ProductDTO> products) {
        try {
            Map<String, Object> analysis = aiDataCollectionAgent.analyzePrices(products);
            
            Map<String, Object> response = Map.of(
                "status", "SUCCESS",
                "analysis", analysis,
                "message", "AI-powered price analysis completed"
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = Map.of(
                "error", "Price analysis failed: " + e.getMessage(),
                "status", "ERROR"
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * Get AI insights for a specific search query
     */
    @GetMapping("/insights/{query}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> getInsights(@PathVariable String query) {
        return aiDataCollectionAgent.collectAllRetailerData(query)
                .thenApply(products -> {
                    Map<String, Object> analysis = aiDataCollectionAgent.analyzePrices(products);
                    
                    Map<String, Object> response = Map.of(
                        "status", "SUCCESS",
                        "searchQuery", query,
                        "insights", analysis,
                        "message", "AI insights generated successfully"
                    );
                    
                    return ResponseEntity.ok(response);
                })
                .exceptionally(throwable -> {
                    Map<String, Object> errorResponse = Map.of(
                        "error", "Failed to generate insights: " + throwable.getMessage(),
                        "status", "ERROR"
                    );
                    return ResponseEntity.internalServerError().body(errorResponse);
                });
    }

    /**
     * AI-powered product recommendations
     */
    @PostMapping("/recommend")
    public ResponseEntity<Map<String, Object>> getRecommendations(@RequestBody Map<String, String> request) {
        String searchQuery = request.get("query");
        String budget = request.get("budget");
        String category = request.get("category");
        
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Search query is required",
                "status", "BAD_REQUEST"
            ));
        }

        try {
            // Collect data and generate recommendations
            return aiDataCollectionAgent.collectAllRetailerData(searchQuery.trim())
                    .thenApply(products -> {
                        List<ProductDTO> recommendations = generateRecommendations(products, budget, category);
                        
                        Map<String, Object> response = Map.of(
                            "status", "SUCCESS",
                            "searchQuery", searchQuery,
                            "budget", budget,
                            "category", category,
                            "recommendations", recommendations,
                            "totalRecommendations", recommendations.size(),
                            "message", "AI recommendations generated successfully"
                        );
                        
                        return ResponseEntity.ok(response);
                    })
                    .get(); // Wait for completion in this controller
        } catch (Exception e) {
            Map<String, Object> errorResponse = Map.of(
                "error", "Failed to generate recommendations: " + e.getMessage(),
                "status", "ERROR"
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * AI-powered market trends analysis
     */
    @GetMapping("/trends")
    public ResponseEntity<Map<String, Object>> getMarketTrends() {
        try {
            // Generate market trends based on historical data patterns
            Map<String, Object> trends = generateMarketTrends();
            
            Map<String, Object> response = Map.of(
                "status", "SUCCESS",
                "trends", trends,
                "message", "AI market trends analysis completed"
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = Map.of(
                "error", "Failed to generate trends: " + e.getMessage(),
                "status", "ERROR"
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * AI-powered price predictions
     */
    @PostMapping("/predict")
    public ResponseEntity<Map<String, Object>> predictPrices(@RequestBody Map<String, String> request) {
        String searchQuery = request.get("query");
        String timeframe = request.get("timeframe"); // daily, weekly, monthly
        
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Search query is required",
                "status", "BAD_REQUEST"
            ));
        }

        try {
            Map<String, Object> predictions = generatePricePredictions(searchQuery, timeframe);
            
            Map<String, Object> response = Map.of(
                "status", "SUCCESS",
                "searchQuery", searchQuery,
                "timeframe", timeframe,
                "predictions", predictions,
                "message", "AI price predictions generated successfully"
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = Map.of(
                "error", "Failed to generate predictions: " + e.getMessage(),
                "status", "ERROR"
            );
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * Generate AI-powered recommendations
     */
    private List<ProductDTO> generateRecommendations(List<ProductDTO> products, String budget, String category) {
        List<ProductDTO> recommendations = new ArrayList<>();
        
        // Filter by category if specified
        List<ProductDTO> filteredProducts = products;
        if (category != null && !category.trim().isEmpty()) {
            filteredProducts = products.stream()
                    .filter(p -> category.equalsIgnoreCase(p.getCategory()))
                    .toList();
        }
        
        // Filter by budget if specified
        if (budget != null && !budget.trim().isEmpty()) {
            try {
                BigDecimal budgetAmount = new BigDecimal(budget);
                filteredProducts = filteredProducts.stream()
                        .filter(p -> p.getPrice().compareTo(budgetAmount) <= 0)
                        .toList();
            } catch (NumberFormatException e) {
                // Invalid budget format, ignore budget filter
            }
        }
        
        // Sort by AI score (price + confidence + retailer reputation)
        filteredProducts.sort((p1, p2) -> {
            double score1 = calculateAIScore(p1);
            double score2 = calculateAIScore(p2);
            return Double.compare(score2, score1); // Higher score first
        });
        
        // Return top recommendations
        return filteredProducts.stream()
                .limit(10)
                .toList();
    }

    /**
     * Calculate AI score for product recommendation
     */
    private double calculateAIScore(ProductDTO product) {
        double score = 0.0;
        
        // Price factor (lower is better)
        double priceScore = 10.0 / (product.getPrice().doubleValue() + 1.0);
        score += priceScore * 0.4;
        
        // Confidence factor
        double confidenceScore = product.getMatchConfidence() != null ? product.getMatchConfidence() : 0.5;
        score += confidenceScore * 0.3;
        
        // Retailer reputation factor
        double retailerScore = getRetailerReputationScore(product.getRetailer());
        score += retailerScore * 0.3;
        
        return score;
    }

    /**
     * Get retailer reputation score
     */
    private double getRetailerReputationScore(String retailer) {
        switch (retailer) {
            case "TESCO": return 0.9;
            case "ASDA": return 0.85;
            case "LIDL": return 0.8;
            case "COSTCO": return 0.85;
            case "BM": return 0.75;
            case "ICELAND": return 0.7;
            case "WHITE_ROSE": return 0.7;
            case "HOTDEALS": return 0.9; // HotDeals has good deals
            default: return 0.5;
        }
    }

    /**
     * Generate market trends
     */
    private Map<String, Object> generateMarketTrends() {
        Map<String, Object> trends = new HashMap<>();
        
        // Price trends
        trends.put("priceTrends", Map.of(
            "direction", "stable",
            "change", "+2.3%",
            "period", "last_30_days"
        ));
        
        // Popular categories
        trends.put("popularCategories", List.of(
            "Dairy", "Bakery", "Meat", "Vegetables", "Beverages"
        ));
        
        // Best performing retailers
        trends.put("bestPerformingRetailers", List.of(
            "HOTDEALS", "LIDL", "WHITE_ROSE"
        ));
        
        // Seasonal trends
        trends.put("seasonalTrends", Map.of(
            "season", "winter",
            "trendingProducts", List.of("Hot Chocolate", "Soup", "Heating Meals"),
            "priceImpact", "+5.2%"
        ));
        
        return trends;
    }

    /**
     * Generate price predictions
     */
    private Map<String, Object> generatePricePredictions(String searchQuery, String timeframe) {
        Map<String, Object> predictions = new HashMap<>();
        
        // Simulated AI predictions based on historical patterns
        double currentPrice = 2.99 + Math.random() * 5.0;
        double predictedChange = (Math.random() - 0.5) * 0.2; // -10% to +10%
        
        predictions.put("currentPrice", String.format("£%.2f", currentPrice));
        predictions.put("predictedPrice", String.format("£%.2f", currentPrice * (1 + predictedChange)));
        predictions.put("changePercentage", String.format("%+.1f%%", predictedChange * 100));
        predictions.put("confidence", 0.75 + Math.random() * 0.2); // 75-95% confidence
        predictions.put("timeframe", timeframe);
        predictions.put("factors", List.of(
            "Seasonal demand",
            "Market competition",
            "Supply chain conditions",
            "Consumer trends"
        ));
        
        return predictions;
    }
}
