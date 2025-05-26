package com.playwright;

import com.microsoft.playwright.junit.UsePlaywright;

import org.junit.jupiter.api.Test;

import com.microsoft.playwright.*;

@UsePlaywright
public class testJUnitIntegration {
    @Test
    void basicTest(BrowserContext browserContext) {
        Page page = browserContext.newPage();
        page.navigate("https://playwright.dev");
        page.click("'Get started'");
    }
}