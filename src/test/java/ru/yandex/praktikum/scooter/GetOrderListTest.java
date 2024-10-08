package ru.yandex.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.praktikum.scooter.orders.OrderChecks;
import ru.yandex.praktikum.scooter.orders.OrderClient;

public class GetOrderListTest {

    private OrderChecks checks = new OrderChecks();
    private OrderClient client = new OrderClient();

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderList(){
        ValidatableResponse getOrderList = client.getOrderList();
        checks.checkGetOrderList(getOrderList);
    }
}