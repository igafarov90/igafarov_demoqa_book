import com.codeborne.selenide.Selenide;
import helpers.TestDataService;
import helpers.WithLogin;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
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
                open("/profile")
        );
        Selenide.dismiss();
        step("Кликнуть по кнопке 'delete all books'", () ->
                $(byTagAndText("button", "Delete All Books")).click()
        );

        step("Сабмит модального окна с удалением книги", () -> {
            $("#closeSmallModal-ok").click();

        });

        step("Проверить, что таблица пуста", () ->
                $(".rt-tbody").$$(".rt-td").get(0).shouldHave(text(" "))
        );


    }


}
