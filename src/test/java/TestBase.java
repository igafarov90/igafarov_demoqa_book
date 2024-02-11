import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriverConfig;
import helpers.Attach;
import helpers.AuthorizationService;
import helpers.Holder;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;


public class TestBase {
    final static WebDriverConfig webDriverConfig = ConfigFactory.create(WebDriverConfig.class, System.getProperties());

    @BeforeAll
    public static void setUp() {

        RestAssured.baseURI = webDriverConfig.baseUrl();
        Holder.auth = AuthorizationService.authByApi();
        Configuration.baseUrl = webDriverConfig.baseUrl();
        Configuration.browser = webDriverConfig.browser();
        Configuration.browserSize = webDriverConfig.browserVersion();
        Configuration.pageLoadStrategy = "eager";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        if (webDriverConfig.isRemote()) {
            Configuration.remote = String.valueOf(webDriverConfig.remoteUrl());
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;

        }
    }

    @AfterEach
    protected void afterEach() {
        Attach.screenshotAs("Last screenshot");
        Attach.addVideo();
        if (Configuration.browser.equals("CHROME")) {
            Attach.browserConsoleLogs();
        }
    }
}

