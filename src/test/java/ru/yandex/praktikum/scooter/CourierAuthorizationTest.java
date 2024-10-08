package ru.yandex.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.scooter.courier.Courier;
import ru.yandex.praktikum.scooter.courier.CourierChecks;
import ru.yandex.praktikum.scooter.courier.CourierClient;
import ru.yandex.praktikum.scooter.courier.CourierCredentials;

public class CourierAuthorizationTest {

    private Courier courier;
    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    private int courierId;

    @Before
    @DisplayName("Создание курьера перед тестом")
    public void setUp() {
        courier = Courier.randomCourier();
        ValidatableResponse response = client.createCourier(courier);
        check.checkCreated(response);
    }

    @After
    @DisplayName("Удаление курьера после теста")
    public void tearDown() {
        if (courierId != 0) {
            client.delete(courierId);
        }
    }

    @Test
    @DisplayName("Курьер может авторизоваться: передаем для авторизации все обязательные поля")
    public void successfulAuthorization() {
        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("Если авторизоваться под несуществующим пользователем, запрос возвращает ошибку")
    public void nonExistentCourierAuthorization() {
        CourierCredentials creds = new CourierCredentials("nonExistentCourier", "nonExistentPassword");
        ValidatableResponse loginResponse = client.logIn(creds);
        check.checkLoggedInWithIncorrectData(loginResponse);
    }

    @Test
    @DisplayName("Cистема вернёт ошибку, если неправильно указать логин")
    public void incorrectLoginInAuthorization() {
        CourierCredentials incorrectLoginCreds = new CourierCredentials("incorrectLogin", courier.getPassword());
        ValidatableResponse loginResponse = client.logIn(incorrectLoginCreds);
        check.checkLoggedInWithIncorrectData(loginResponse);
    }

    @Test
    @DisplayName("Cистема вернёт ошибку, если неправильно указать пароль")
    public void incorrectPasswordInAuthorization(){
        CourierCredentials incorrectPasswordCreds = new CourierCredentials(courier.getLogin(), "incorrectPassword");
        ValidatableResponse loginResponse = client.logIn(incorrectPasswordCreds);
        check.checkLoggedInWithIncorrectData(loginResponse);
    }

    @Test
    @DisplayName("Cистема вернёт ошибку, если отправить запрос без логина")
    public void emptyLoginInAuthorization(){
        CourierCredentials emptyLoginCreds = new CourierCredentials("", courier.getPassword());
        ValidatableResponse loginResponse = client.logIn(emptyLoginCreds);
        check.checkLoggedInWithoutData(loginResponse);
    }

    @Test
    @DisplayName("Cистема вернёт ошибку, если отправить запрос без пароля")
    public void emptyPasswordInAuthorization(){
        CourierCredentials emptyPasswordCreds = new CourierCredentials(courier.getLogin(), "");
        ValidatableResponse loginResponse = client.logIn(emptyPasswordCreds);
        check.checkLoggedInWithoutData(loginResponse);
    }


    @Test
    @DisplayName("Cистема вернёт ошибку, если отправить запрос с null в логине")
    public void nullLoginInAuthorization(){
        CourierCredentials nullLoginCreds = new CourierCredentials(null, courier.getPassword());
        ValidatableResponse loginResponse = client.logIn(nullLoginCreds);
        check.checkLoggedInWithoutData(loginResponse);
    }

    @Test
    @DisplayName("Cистема вернёт ошибку, если отправить запрос с null в пароле")
    public void nullPasswordInAuthorization(){
        CourierCredentials nullPasswordCreds = new CourierCredentials(courier.getLogin(), null);
        ValidatableResponse loginResponse = client.logIn(nullPasswordCreds);
        check.checkLoggedInWithoutData(loginResponse);
    }

}