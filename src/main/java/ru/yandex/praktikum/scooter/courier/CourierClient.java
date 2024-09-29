package ru.yandex.praktikum.scooter.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.scooter.main.Client;

import java.util.Map;

public class CourierClient extends Client {

    private static final String COURIER_PATH = "courier";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse logIn(CourierCredentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(COURIER_PATH + "/login")
                .then().log().all();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(int id) {
        return spec()
                .body(Map.of("id", id))
                .when()
                .delete(COURIER_PATH + "/" + id)
                .then().log().all();
    }
}