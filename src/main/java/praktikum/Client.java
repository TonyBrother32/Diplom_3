package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class Client extends ApiClient {
    private final String REGISTER_PATH = "api/auth/register";
    private final String LOGIN_PATH = "api/auth/login";
    private final String AUTH_CLIENT = "api/auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse createClient(ClientGenerator client) {
        return given()
                .spec(requestSpecification())
                .body(client)
                .when()
                .post(REGISTER_PATH)
                .then().log().ifError();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse loginClient(ClientGenerator client) {
        return given()
                .spec(requestSpecification())
                .body(client)
                .when()
                .post(LOGIN_PATH)
                .then().log().ifError();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteClient(String token) {
        return given()
                .spec(requestSpecification())
                .header("Authorization", token)
                .when()
                .delete(AUTH_CLIENT)
                .then().log().ifError();
    }
}
