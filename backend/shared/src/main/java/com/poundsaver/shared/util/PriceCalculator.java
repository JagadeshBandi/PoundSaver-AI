package com.poundsaver.shared.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceCalculator {
    
    private static final Pattern QUANTITY_PATTERN = Pattern.compile("(\\d+(?:\\.\\d+)?)\\s*(ml|l|g|kg|pint|litre|liter)", Pattern.CASE_INSENSITIVE);
    
    public static BigDecimal calculatePricePerUnit(BigDecimal price, String productName, String size) {
        if (price == null || price.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        Double quantity = extractQuantity(productName, size);
        if (quantity == null || quantity == 0) {
            return price;
        }
        
        return price.divide(BigDecimal.valueOf(quantity), 4, RoundingMode.HALF_UP);
    }
    
    public static Double extractQuantity(String productName, String size) {
        String text = (productName + " " + (size != null ? size : "")).toLowerCase();
        
        Matcher matcher = QUANTITY_PATTERN.matcher(text);
        if (matcher.find()) {
            double value = Double.parseDouble(matcher.group(1));
            String unit = matcher.group(2).toLowerCase();
            
            return switch (unit) {
                case "kg" -> value * 1000;
                case "l", "litre", "liter" -> value * 1000;
                case "pint" -> value * 568.26;
                default -> value;
            };
        }
        
        return null;
    }
    
    public static String normalizeUnit(String unit) {
        if (unit == null) return "per item";
        
        return switch (unit.toLowerCase()) {
            case "kg", "g" -> "per 100g";
            case "l", "ml", "litre", "liter" -> "per litre";
            case "pint" -> "per pint";
            default -> "per item";
        };
    }
}
