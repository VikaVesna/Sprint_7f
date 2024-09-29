package ru.yandex.praktikum.scooter.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.scooter.main.Client;

public class OrderClient extends Client {

    public static final String ORDER_PATH = "orders";

    @Step("Создание нового заказа")
    public ValidatableResponse createNewOrder (CreateNewOrder newOrder) {
        return spec()
                .body(newOrder)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return spec()
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }
}