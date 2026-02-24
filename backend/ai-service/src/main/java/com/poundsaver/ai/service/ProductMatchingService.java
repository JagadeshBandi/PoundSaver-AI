package com.poundsaver.ai.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ProductMatchingService {

    private final ChatLanguageModel chatModel;
    private static final Pattern BRAND_PATTERN = Pattern.compile("^([A-Z][a-zA-Z&]+)\\s+");

    public ProductMatchingService(@Value("${openai.api.key}") String apiKey) {
        this.chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gpt-4")
                .temperature(0.1)
                .build();
    }

    public String normalizeProductName(String productName) {
        log.info("Normalizing product name: {}", productName);

        try {
            String prompt = String.format(
                    "Normalize this product name to a standard format. " +
                    "Remove retailer-specific branding but keep the actual brand name, size, and type. " +
                    "Product: '%s'. " +
                    "Return only the normalized name, nothing else.",
                    productName
            );

            String normalized = chatModel.generate(prompt);
            log.info("Normalized '{}' to '{}'", productName, normalized);
            return normalized.trim();

        } catch (Exception e) {
            log.error("Failed to normalize product name using AI: {}", e.getMessage());
            return fallbackNormalization(productName);
        }
    }

    public String extractBrand(String productName) {
        log.info("Extracting brand from: {}", productName);

        try {
            String prompt = String.format(
                    "Extract only the brand name from this product: '%s'. " +
                    "Return only the brand name, nothing else. " +
                    "If no brand is found, return 'Unknown'.",
                    productName
            );

            String brand = chatModel.generate(prompt);
            log.info("Extracted brand '{}' from '{}'", brand, productName);
            return brand.trim();

        } catch (Exception e) {
            log.error("Failed to extract brand using AI: {}", e.getMessage());
            return fallbackBrandExtraction(productName);
        }
    }

    public double calculateMatchConfidence(String product1, String product2) {
        log.info("Calculating match confidence between '{}' and '{}'", product1, product2);

        try {
            String prompt = String.format(
                    "Compare these two products and rate how similar they are on a scale of 0.0 to 1.0. " +
                    "Product 1: '%s'. Product 2: '%s'. " +
                    "Return only a decimal number between 0.0 and 1.0, nothing else.",
                    product1, product2
            );

            String response = chatModel.generate(prompt);
            double confidence = Double.parseDouble(response.trim());
            log.info("Match confidence: {}", confidence);
            return confidence;

        } catch (Exception e) {
            log.error("Failed to calculate match confidence using AI: {}", e.getMessage());
            return fallbackMatchConfidence(product1, product2);
        }
    }

    private String fallbackNormalization(String productName) {
        return productName
                .replaceAll("(?i)(tesco|asda|lidl|costco|b&m)\\s+", "")
                .replaceAll("\\s+", " ")
                .trim()
                .toLowerCase();
    }

    private String fallbackBrandExtraction(String productName) {
        Matcher matcher = BRAND_PATTERN.matcher(productName);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "Unknown";
    }

    private double fallbackMatchConfidence(String product1, String product2) {
        String normalized1 = fallbackNormalization(product1);
        String normalized2 = fallbackNormalization(product2);
        
        if (normalized1.equals(normalized2)) {
            return 1.0;
        }
        
        String[] words1 = normalized1.split("\\s+");
        String[] words2 = normalized2.split("\\s+");
        
        int matchCount = 0;
        for (String word1 : words1) {
            for (String word2 : words2) {
                if (word1.equals(word2)) {
                    matchCount++;
                    break;
                }
            }
        }
        
        int totalWords = Math.max(words1.length, words2.length);
        return totalWords > 0 ? (double) matchCount / totalWords : 0.0;
    }
}
