package helpers;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
public class LoginExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) {

    open("/favicon.ico");
    getWebDriver().manage().addCookie(new Cookie("userID", Holder.auth.getUserId()));
    getWebDriver().manage().addCookie(new Cookie("expires", Holder.auth.getExpires()));
    getWebDriver().manage().addCookie(new Cookie("token", Holder.auth.getToken()));
}

}
