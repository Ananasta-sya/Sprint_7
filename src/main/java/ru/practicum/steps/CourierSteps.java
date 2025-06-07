package ru.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.practicum.models.Courier;

import static io.restassured.RestAssured.given;

public class CourierSteps {
    public static final String URL = "http://qa-scooter.praktikum-services.ru/";
    public static final String CREATE_COURIER = "/api/v1/courier";
    public static final String LOGIN_COURIER = "/api/v1/courier/login";
    public static final String DELETE_COURIER = "/api/v1/courier/{id}";

    @Step("Создание нового курьера")
    public ValidatableResponse createCourier(Courier courier){
        return given()
                .baseUri(URL)
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(CREATE_COURIER)
                .then();
    }
    @Step("Вход в систему под логином курьера")
    public ValidatableResponse loginCourier(Courier courier){
        return given()
                .baseUri(URL)
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(LOGIN_COURIER)
                .then();
    }

    @Step("Удаление курьера из базы данных")
    public ValidatableResponse deleteCourier(Integer id) {
        return given()
                .baseUri(URL)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .delete(DELETE_COURIER)
                .then();
    }
}
