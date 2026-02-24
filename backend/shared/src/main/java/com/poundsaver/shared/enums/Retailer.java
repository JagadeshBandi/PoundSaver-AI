package com.poundsaver.shared.enums;

public enum Retailer {
    TESCO("Tesco", "https://www.tesco.com"),
    ASDA("Asda", "https://www.asda.com"),
    LIDL("Lidl", "https://www.lidl.co.uk"),
    COSTCO("Costco", "https://www.costco.co.uk"),
    BM("B&M", "https://www.bmstores.co.uk"),
    ICELAND("Iceland", "https://www.iceland.co.uk"),
    WHITE_ROSE("White Rose", "https://www.whiterose.co.uk"),
    HOTDEALS("HotDeals", "https://www.hotdealsuk.com");

    private final String displayName;
    private final String baseUrl;

    Retailer(String displayName, String baseUrl) {
        this.displayName = displayName;
        this.baseUrl = baseUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
