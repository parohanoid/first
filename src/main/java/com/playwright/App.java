package com.playwright;

import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            BrowserType browserType = playwright.chromium();
            Browser browser = browserType.launch(new BrowserType.LaunchOptions()
                    .setHeadless(false).setSlowMo(50));

            Page page = browser.newPage();
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
}
