package ru.yandex.praktikum.scooter.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.*;

public class OrderChecks {
    @Step("Проверка успешного создания заказа")
    public void checkCreateNewOrder(ValidatableResponse createNewOrderResponse) {
        int track = createNewOrderResponse
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .path("track");
        assertNotEquals(0, track);
    }

    @Step("Получение списка заказов")
    public void checkGetOrderList(ValidatableResponse createNewOrderResponse) {
        List<Object> orderList = createNewOrderResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("orders");
        assertTrue("Список заказов пусть", !orderList.isEmpty());
    }
}