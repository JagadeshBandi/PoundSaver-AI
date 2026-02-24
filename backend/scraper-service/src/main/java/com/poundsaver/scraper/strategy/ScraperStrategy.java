package com.poundsaver.scraper.strategy;

import com.microsoft.playwright.Page;
import com.poundsaver.shared.dto.ProductDTO;

import java.util.List;

public interface ScraperStrategy {
    List<ProductDTO> scrape(Page page, String searchQuery);
}
