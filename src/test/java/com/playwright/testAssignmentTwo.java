package com.playwright;

import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.Locator;


public class testAssignmentTwo {
    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        // Start tracing before creating / navigating a page
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        // Stop tracing and export it into a zip archive.
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
        context.close();
    }

    @Test
    void selectRandomCheckBoxes() {
        page.navigate("http://www.tizag.com/htmlT/htmlcheckboxes.php");

        
        page.locator("(//input[@name='sports'])[1]").waitFor();
        List<Locator> allCheckboxes = page.locator("//input[@name='sports']").all();

        Random r = new Random();

        int firstRandom = r.nextInt(allCheckboxes.size());
        Locator firstRandomLocator = allCheckboxes.get(r.nextInt(allCheckboxes.size()));
        firstRandomLocator.check();
        allCheckboxes.remove(firstRandom);

        int secondRandom = r.nextInt(allCheckboxes.size());
        Locator secondRandomLocator = allCheckboxes.get(r.nextInt(allCheckboxes.size()));
        secondRandomLocator.check();
        allCheckboxes.remove(secondRandom);

        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshot.png")));

    }
}
