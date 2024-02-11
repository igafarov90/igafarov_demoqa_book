package helpers;

import io.restassured.RestAssured;

import api.models.BooksModel;
import api.models.IsbnsListModel;
import api.models.SuccessAddBooksModel;
import api.models.UserBooksModel;

import java.util.ArrayList;
import java.util.List;


import static api.specifications.Specification.*;
import static helpers.Holder.*;
import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;

public class TestDataService {

    static final String GIT_POKET_GUIDE = "9781449325862";
    static final String LEARNING_JAVA_SCRIPT_DESIGN_PATTERNS = "9781449331818";
    static final String SPEAKING_JAVASCRIPT = "9781449365035";


    public static UserBooksModel getUserBooksList(String userId, String token) {

        return given(requestSpec())
                .when()
                .config(RestAssured.config().logConfig(logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails()))
                .header("Authorization", "Bearer " + token)
                .get("/Account/v1/User/" + userId)
                .then()
                .extract().as(UserBooksModel.class);
    }

    public static SuccessAddBooksModel addBook(String userId, String token) {

        List<IsbnsListModel> isbnsListModel = new ArrayList<>();
        isbnsListModel.add(IsbnsListModel.builder().isbn(GIT_POKET_GUIDE).build());
        isbnsListModel.add(IsbnsListModel.builder().isbn(LEARNING_JAVA_SCRIPT_DESIGN_PATTERNS).build());
        isbnsListModel.add(IsbnsListModel.builder().isbn(SPEAKING_JAVASCRIPT).build());

        BooksModel booksModel = BooksModel.builder()
                .userId(userId)
                .collectionOfIsbns(isbnsListModel)
                .build();

        return given(requestSpec())
                .when()
                .config(RestAssured.config().logConfig(logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails()))
                .header("Authorization", "Bearer " + token)
                .body(booksModel)
                .post("/BookStore/v1/Books")
                .then()
                .spec(responseSpecCreated201())
                .extract().as(SuccessAddBooksModel.class);
    }

    public static void addBookForUser() {

        booksList = getUserBooksList(auth.getUserId(), auth.getToken());

        List books = booksList.getBooks();
        System.out.println(books.size());

        if (books.isEmpty()) {
            System.out.println("у user нет книг, создаю 3 книги");
            addBook(auth.getUserId(), auth.getToken());
        } else {
            System.out.println("у user уже есть книги");
            addBook(auth.getUserId(), auth.getToken());
        }

    }
}
