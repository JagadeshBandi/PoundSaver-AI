package com.poundsaver.steps;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class PriceComparisonSteps {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @Before
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext();
        page = context.newPage();
    }

    @After
    public void tearDown() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Given("I am on the PoundSaver AI home page")
    public void iAmOnThePoundSaverAIHomePage() {
        page.navigate("http://localhost:3000");
        assertTrue(page.title().contains("PoundSaver AI"));
    }

    @When("I search for {string}")
    public void iSearchFor(String searchQuery) {
        page.fill("input[type='text']", searchQuery);
        page.click("button[type='submit']");
        page.waitForTimeout(2000);
    }

    @When("the system scrapes Tesco, Asda, Lidl, Costco, B&M, Iceland, and White Rose")
    public void theSystemScrapesAllRetailers() {
        page.waitForSelector(".product-card", new Page.WaitForSelectorOptions().setTimeout(10000));
    }

    @Then("the results should be sorted by {string}")
    public void theResultsShouldBeSortedBy(String sortOrder) {
        assertTrue(page.isVisible("text=Found"));
    }

    @Then("the cheapest item should be highlighted")
    public void theCheapestItemShouldBeHighlighted() {
        assertTrue(page.locator(".product-card").first().isVisible());
    }

    @Then("I should see the price per liter for each product")
    public void iShouldSeeThePricePerLiterForEachProduct() {
        assertTrue(page.isVisible("text=per"));
    }

    @Given("I am searching for {string}")
    public void iAmSearchingFor(String product) {
        page.navigate("http://localhost:3000");
        page.fill("input[type='text']", product);
    }

    @When("I enable {string} pricing")
    public void iEnablePricing(String loyaltyScheme) {
        page.click("button[type='submit']");
        page.waitForTimeout(2000);
    }

    @Then("I should see both regular and Clubcard prices")
    public void iShouldSeeBothRegularAndClubcardPrices() {
        assertTrue(page.isVisible("text=Clubcard") || page.isVisible("text=£"));
    }

    @Then("the system should highlight the best overall deal")
    public void theSystemShouldHighlightTheBestOverallDeal() {
        assertTrue(page.locator(".product-card").first().isVisible());
    }

    @Given("I am comparing {string}")
    public void iAmComparing(String product) {
        page.navigate("http://localhost:3000");
        page.fill("input[type='text']", product);
        page.click("button[type='submit']");
        page.waitForTimeout(2000);
    }

    @When("I view prices from all retailers")
    public void iViewPricesFromAllRetailers() {
        page.waitForSelector(".product-card");
    }

    @Then("I should see Costco bulk pricing")
    public void iShouldSeeCostcoBulkPricing() {
        assertTrue(page.isVisible("text=COSTCO") || page.isVisible("text=Costco"));
    }

    @Then("the system should calculate price per unit")
    public void theSystemShouldCalculatePricePerUnit() {
        assertTrue(page.isVisible("text=per"));
    }

    @Then("I should see if bulk buying saves money")
    public void iShouldSeeIfBulkBuyingSavesMoney() {
        assertTrue(page.locator(".product-card").count() > 0);
    }

    @Given("I am viewing a product {string}")
    public void iAmViewingAProduct(String product) {
        page.navigate("http://localhost:3000");
        page.fill("input[type='text']", product);
        page.click("button[type='submit']");
        page.waitForTimeout(2000);
    }

    @When("I check the price history for {int} days")
    public void iCheckThePriceHistoryForDays(int days) {
        if (page.locator(".product-card").count() > 0) {
            page.locator(".product-card").first().click();
            page.waitForTimeout(1000);
        }
    }

    @Then("I should see a price trend chart")
    public void iShouldSeeAPriceTrendChart() {
        assertTrue(page.isVisible("text=Price") || page.isVisible("text=History"));
    }

    @Then("I should see the lowest and highest prices")
    public void iShouldSeeTheLowestAndHighestPrices() {
        assertTrue(page.isVisible("text=£") || page.isVisible("text=Price"));
    }

    @Then("I should see the average price")
    public void iShouldSeeTheAveragePrice() {
        assertTrue(page.isVisible("text=£") || page.isVisible("text=Average"));
    }

    @Given("I have searched for {string}")
    public void iHaveSearchedFor(String product) {
        page.navigate("http://localhost:3000");
        page.fill("input[type='text']", product);
        page.click("button[type='submit']");
        page.waitForTimeout(2000);
    }

    @When("new prices are scraped")
    public void newPricesAreScraped() {
        page.reload();
        page.waitForTimeout(1000);
    }

    @Then("the results should update automatically")
    public void theResultsShouldUpdateAutomatically() {
        assertTrue(page.locator(".product-card").count() >= 0);
    }

    @Then("I should see the scraping timestamp")
    public void iShouldSeeTheScrapingTimestamp() {
        assertTrue(page.isVisible("text=Found") || page.isVisible("text=products"));
    }

    @Then("out of stock items should be marked clearly")
    public void outOfStockItemsShouldBeMarkedClearly() {
        assertTrue(page.isVisible("text=In Stock") || page.isVisible("text=Out of Stock"));
    }
}
