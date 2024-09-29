package ru.yandex.praktikum.scooter;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.scooter.orders.CreateNewOrder;
import ru.yandex.praktikum.scooter.orders.OrderChecks;
import ru.yandex.praktikum.scooter.orders.OrderClient;

import java.util.List;

@RunWith(Parameterized.class)

public class CreateNewOrderTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    private OrderChecks checks = new OrderChecks();
    private OrderClient client = new OrderClient();

    public CreateNewOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2024-09-28", "Saske, come back to Konoha", List.of("BLACK")},  //    можно указать один из цветов — BLACK;
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2024-09-28", "Saske, come back to Konoha", List.of("GREY")},   //    можно указать один из цветов — GREY;
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2024-09-28", "Saske, come back to Konoha", List.of("BLACK", "GREY")},  //    можно указать оба цвета;
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2024-09-28", "Saske, come back to Konoha", List.of("")},  //    можно совсем не указывать цвет;
        };
    }

    @Test
    @Step("Успешное создание заказа")
    public void createSuccessfulOrder() {
        CreateNewOrder newOrder = new CreateNewOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse response = client.createNewOrder(newOrder);
        checks.checkCreateNewOrder(response);
    }
}