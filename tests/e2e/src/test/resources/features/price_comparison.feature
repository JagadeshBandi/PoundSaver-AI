Feature: Supermarket Price Comparison

  Scenario: Finding the cheapest milk in the UK
    Given I am on the PoundSaver AI home page
    When I search for "Whole Milk 2 pints"
    And the system scrapes Tesco, Asda, Lidl, Costco, B&M, Iceland, and White Rose
    Then the results should be sorted by "Price Low to High"
    And the cheapest item should be highlighted
    And I should see the price per liter for each product

  Scenario: Loyalty card price comparison
    Given I am searching for "Heinz Baked Beans 400g"
    When I enable "Tesco Clubcard" pricing
    Then I should see both regular and Clubcard prices
    And the system should highlight the best overall deal

  Scenario: Bulk savings comparison
    Given I am comparing "Rice 1kg"
    When I view prices from all retailers
    Then I should see Costco bulk pricing
    And the system should calculate price per unit
    And I should see if bulk buying saves money

  Scenario: Price history tracking
    Given I am viewing a product "Bread"
    When I check the price history for 30 days
    Then I should see a price trend chart
    And I should see the lowest and highest prices
    And I should see the average price

  Scenario: Real-time price updates
    Given I have searched for "Eggs"
    When new prices are scraped
    Then the results should update automatically
    And I should see the scraping timestamp
    And out of stock items should be marked clearly
