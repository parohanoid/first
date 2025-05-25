package com.playwright;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            BrowserType browserType = playwright.chromium();
            Browser browser = browserType.launch(new BrowserType.LaunchOptions()
                .setHeadless(false).setSlowMo(50));

            Page page = browser.newPage();
            page.navigate("https://playwright.dev");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("playwright.png", args)));

            // Assert Title
            assertThat(page).hasTitle(Pattern.compile("Playwright"));

            // Create Locator
            Locator getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("GET STARTED"));

            // Assert Attribute
            assertThat(getStarted).hasAttribute("href", "/docs/intro");

            // Click Get Started
            getStarted.click();

            // Assert Heading
            assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Installation"))).isVisible();

            
            page.close();
        }
    }
}

