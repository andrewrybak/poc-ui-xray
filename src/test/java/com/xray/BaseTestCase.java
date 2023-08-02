package com.xray;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.BeforeAll;

public class BaseTestCase {
    // Shared between all tests in this class.
    protected static Playwright playwright;
    protected static Browser browser;
    protected static Page page;


    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
//        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(2000));

//        browser.newContext(new Browser.NewContextOptions().setViewportSize(1792, 1120));
        page = browser.newPage();
        page.navigate("https://dev.apps.mastercontrol.engineering/homepage/");
    }
}