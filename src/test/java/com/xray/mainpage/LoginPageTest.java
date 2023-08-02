package com.xray.mainpage;

import com.github.javafaker.Faker;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.xray.BaseTestCase;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginPageTest extends BaseTestCase {

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @Test
    @Order(1)
    @DisplayName("Verify Login is successful")
    void verifyLoginSuccess() throws InterruptedException {

        String login = System.getenv("login");
        String pwd = System.getenv("password");
//        String login = "";
//        String pwd = "";
        page.locator("[id=\"okta-signin-username\"]").fill(login);
        page.locator("[id=\"okta-signin-password\"]").fill(pwd);
        page.locator("[id=\"okta-signin-submit\"]").click();

        Thread.sleep(10000);

        assertEquals("MasterControl - HomePage", page.title());
    }

    @Test
    @Order(2)
    @DisplayName("Verify Tasks are visible")
    void verifyTasksAreVisible() {
        Locator loc = page.locator("//h2[contains(@class,'MuiTypography-root MuiTypography-h2')]");

        assertTrue(loc.isVisible());
    }

    @Test
    @Order(3)
    @DisplayName("Verify Workflow page is opened")
    void verifyWorkflowPageOpened() throws InterruptedException {
        page.navigate("https://dev.apps.mastercontrol.engineering/aqem/config/new");
        Thread.sleep(5000);
        assertEquals("MasterControl - AQEM", page.title());
    }

    @Test
    @Order(4)
    @DisplayName("Verify Workflow creation")
    void verifyWorkflowCreation() throws InterruptedException {
        Faker faker = new Faker();

        page.getByLabel("Name *").fill(faker.name().username());
        page.getByLabel("Workflow Type *").click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Deviation")).click();
        page.locator("[placeholder=\"Event identifier, ie: CAPA, DEV, etc...\"]").fill(faker.name().title());
        page.locator("//*[contains(text(),'Create Workflow')]").click();

        Thread.sleep(2000);
        Locator loc = page.locator("//h3[contains(@class,'MuiTypography-root MuiTypography-h3')]");

        assertTrue(loc.isVisible());
    }
}