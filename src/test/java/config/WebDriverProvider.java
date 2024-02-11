package config;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Objects;
import java.util.function.Supplier;

public class WebDriverProvider implements Supplier<WebDriver> {

    private final WebDriverConfig config;

    public WebDriverProvider() {
        this.config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());
    }

    @Override
    public WebDriver get() {
        WebDriver driver = createWebDriver();
        driver.get(config.baseUrl());
        return driver;
    }

    private WebDriver createWebDriver() {
        if (Objects.isNull(config.remoteUrl())) {
            if (config.browser().equals(Browser.CHROME.toString())) {
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            } else if (config.browser().equals(Browser.FIREFOX.toString())) {
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            }
        } else {
            if (config.browser().equals(Browser.CHROME.toString())) {
                return new RemoteWebDriver(config.remoteUrl(), new ChromeOptions());
            } else if (config.browser().equals(Browser.FIREFOX.toString())) {
                return new RemoteWebDriver(config.remoteUrl(), new FirefoxOptions());
            }
        }
        throw new RuntimeException("No such browser");
    }

}