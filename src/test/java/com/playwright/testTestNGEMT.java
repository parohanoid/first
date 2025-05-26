package com.playwright;
import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import org.testng.annotations.*;

public class testTestNGEMT {
    Playwright playwright;
    Browser browser;

    BrowserContext context;
    Page page;

    @BeforeClass
    void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false).setSlowMo(50));
    }

    @AfterClass
    void closeBrowser() {
        playwright.close();
    }

    @BeforeMethod
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterMethod
    void closeContext() {
        context.close();
    }

    @Test
    void getAllFlights() {
        page.navigate("https://www.easemytrip.com/");
        page.locator("#frmcity").click();
        page.keyboard().type("NYC", new Keyboard.TypeOptions().setDelay(100));
        page.getByText("New York(NYC)All Airports Airport US Nearby Airport Found").click();
        page.keyboard().type("DEL", new Keyboard.TypeOptions().setDelay(100));
        page.getByText("New Delhi(DEL)Indira Gandhi International Airport India Nearby Airport Found").click();
        
        page.locator(".active-date").first().click();

        page.locator("#rdatelbl").click();
        page.locator(".active-date").first().click();

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();

        page.locator("#Loader div").nth(1).waitFor();

        page.locator("#Loader div").nth(1).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));

        int count = 1;

        for (Locator locator : page.locator("//*[@class='list-main']//span[text()]").all()) {
            if (count % 9 != 0) {
                System.out.print(locator.textContent());
            } else { System.out.println(locator.textContent()); }
            count++;
        }
    }
}
