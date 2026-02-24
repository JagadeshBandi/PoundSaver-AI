package com.poundsaver.scraper.strategy;

import com.poundsaver.shared.enums.Retailer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScraperStrategyFactory {

    private final TescoScraperStrategy tescoStrategy;
    private final AsdaScraperStrategy asdaStrategy;
    private final LidlScraperStrategy lidlStrategy;
    private final CostcoScraperStrategy costcoStrategy;
    private final BMScraperStrategy bmStrategy;
    private final IcelandScraperStrategy icelandStrategy;
    private final WhiteRoseScraperStrategy whiteRoseStrategy;
    private final HotDealsScraperStrategy hotDealsStrategy;

    public ScraperStrategy getStrategy(Retailer retailer) {
        if (retailer == null) {
            throw new IllegalArgumentException("Retailer cannot be null");
        }
        
        return switch (retailer) {
            case TESCO -> tescoStrategy;
            case ASDA -> asdaStrategy;
            case LIDL -> lidlStrategy;
            case COSTCO -> costcoStrategy;
            case BM -> bmStrategy;
            case ICELAND -> icelandStrategy;
            case WHITE_ROSE -> whiteRoseStrategy;
            case HOTDEALS -> hotDealsStrategy;
        };
    }
}
