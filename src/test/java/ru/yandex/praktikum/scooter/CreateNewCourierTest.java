package ru.yandex.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.scooter.courier.Courier;
import ru.yandex.praktikum.scooter.courier.CourierChecks;
import ru.yandex.praktikum.scooter.courier.CourierClient;
import ru.yandex.praktikum.scooter.courier.CourierCredentials;

public class CreateNewCourierTest {

    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    int courierId;

    @After
    @DisplayName("Удаление курьера после теста")
    public void tearDown() {
        if (courierId != 0) {
            client.delete(courierId);
        }
    }

    @Test
    @DisplayName("Курьера можно создать; запрос возвращает правильный код ответа и ok: true;")
    public void createNewCourier() {
        var courier = Courier.randomCourier();
        ValidatableResponse response = client.createCourier(courier);
        check.checkCreated(response);

        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);
    }


    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров; если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void createDuplicateCourier() {
        var courier = Courier.randomCourier();
        ValidatableResponse firstResponse = client.createCourier(courier);
        ValidatableResponse secondResponse = client.createCourier(courier);

        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);

        check.checkDuplicateRequest(secondResponse);
    }


    @Test
    @DisplayName("Запрос возвращает ошибку, если не заполнено поле Логин")
    public void createNewCourierWithoutLogin() {
        var courier = Courier.withoutLogin();
        ValidatableResponse response = client.createCourier(courier);
        check.checkFailed(response);
    }

    @Test
    @DisplayName("Запрос возвращает ошибку, если не заполнено поле Пароль")
    public void createNewCourierWithoutPassword() {
        var courier = Courier.withoutPassword();
        ValidatableResponse response = client.createCourier(courier);
        check.checkFailed(response);
    }


    @Test
    @DisplayName("Запрос возвращает ошибку, если не заполнено поле Имя")
    public void createNewCourierWithoutFirstName() {
        var courier = Courier.withoutFirstName();
        ValidatableResponse response = client.createCourier(courier);
        check.checkFailed(response);
    }


    @Test
    @DisplayName("Запрос возвращает ошибку, если в поле Логин null")
    public void createNewCourierWithNullInLogin() {
        var courier = Courier.nullInLogin();
        ValidatableResponse response = client.createCourier(courier);
        check.checkFailed(response);
    }

    @Test
    @DisplayName("Запрос возвращает ошибку, если в поле Пароль null")
    public void createNewCourierWithNullInPassword() {
        var courier = Courier.nullInPassword();
        ValidatableResponse response = client.createCourier(courier);
        check.checkFailed(response);
    }


    @Test
    @DisplayName("Запрос возвращает ошибку, если в поле Имя null")
    public void createNewCourierWithNullInFirstName() {
        var courier = Courier.nullInFirstName();
        ValidatableResponse response = client.createCourier(courier);
        check.checkFailed(response);
    }
}