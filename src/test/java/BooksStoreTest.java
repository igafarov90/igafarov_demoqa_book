import com.codeborne.selenide.Selenide;
import helpers.TestDataService;
import helpers.WithLogin;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;
import static helpers.Holder.*;
import static io.qameta.allure.Allure.step;

public class BooksStoreTest extends TestBase {

    @WithLogin
    @Test
    void deleteBookFromProfileTest() {

        step("Добавить книги в профайл пользователя " + auth.getUserId(), () ->
                TestDataService.addBookForUser());

        step("Открыть брузер на странице profile", () ->
                open("/profile"));
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");

        step("Кликнуть по кнопке 'delete all books'", () -> {

            if ($(byTagAndText("p", "Consent")).exists()
            ) {
                $(byTagAndText("p", "Consent")).click();
            }
//sleep(6000);
            $(byTagAndText("button", "Delete All Books")).shouldBe(visible, Duration.ofSeconds(10)).click();
        });

        step("Сабмит модального окна с удалением книги", () -> {
            $("#closeSmallModal-ok").click();
            Selenide.confirm();
        });

        step("Проверить, что таблица пуста", () ->
                $(".rt-tbody").$$(".rt-td").get(0).shouldHave(text(" "))
        );

    }

}
