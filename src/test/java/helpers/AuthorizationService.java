package helpers;

import io.restassured.RestAssured;
import api.models.LoginModel;
import api.models.SuccessLoginModel;

import static api.specifications.Specification.requestSpec;
import static api.specifications.Specification.responseSpecOk200;
import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;

public class AuthorizationService {
    public static SuccessLoginModel authByApi(){

        LoginModel loginModel = new LoginModel("igafarov", "!O1r2i3o4n5");
        return
                given(requestSpec())
                        .when()
                        .config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                        .body(loginModel)
                        .post("/Account/v1/Login")
                        .then()
                        .spec(responseSpecOk200())
                        .extract()
                        .as(SuccessLoginModel.class);
    }

}
