package ru.yandex.praktikum.scooter.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;

public class CourierChecks {
    @Step("Проверка успешного создания курьера")
    public void checkCreated(ValidatableResponse response) {
        boolean created = response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .path("ok");
        assertTrue("Курьер не создан", created);
    }


    @Step("Ошибка при создании двух одинаковых курьеров")
    public void checkDuplicateRequest(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное сообщение об ошибке", "Этот логин уже используется", body.get("message"));
    }


    @Step("Ошибка при создании курьера без заполнения одного из полей")
    public void checkFailed(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное сообщение об ошибке","Недостаточно данных для создания учетной записи", body.get("message"));
    }


    @Step("Успешная авторизация курьера")
    public int checkLoggedIn(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("id");
        assertNotEquals("id курьера равен 0",0, id);
        return id;
    }

    @Step("Ошибка авторизации курьера при неверном логине или пароле")
    public void checkLoggedInWithIncorrectData(ValidatableResponse loginResponse) {
        var body = loginResponse
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное сообщение об ошибке","Учетная запись не найдена", body.get("message"));
    }


    @Step("Ошибка авторизации курьера, если отправить запрос без логина или пароля")
    public void checkLoggedInWithoutData(ValidatableResponse loginResponse) {
        var body = loginResponse
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .body().as(Map.class);
        assertEquals("Неверное сообщение об ошибке","Недостаточно данных для входа", body.get("message"));
    }
}