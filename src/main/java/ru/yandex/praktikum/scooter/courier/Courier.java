package ru.yandex.praktikum.scooter.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {

    private String login;
    private String password;
    private String firstName;

    public Courier() {
    }

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier randomCourier() {
        String login = RandomStringUtils.randomAlphanumeric(3, 7);
        String password = RandomStringUtils.randomAlphanumeric(3, 7);
        String firstName = RandomStringUtils.randomAlphanumeric(3, 7);
        return new Courier(login, password, firstName);
    }

    public static Courier withoutLogin() {
        return new Courier("", "1234", "newTestCourier");
    }

    public static Courier withoutPassword() {
        return new Courier("newTestCourier" + RandomStringUtils.randomAlphanumeric(3, 7), "", "newTestCourier");
    }

    public static Courier withoutFirstName() {
        return new Courier("newTestCourier" + RandomStringUtils.randomAlphanumeric(3, 7), "1234", "");
    }

    public static Courier nullInLogin() {
        return new Courier(null, "1234", "newTestCourier");
    }

    public static Courier nullInPassword() {
        return new Courier("newTestCourier" + RandomStringUtils.randomAlphanumeric(3, 7), null, "newTestCourier");
    }

    public static Courier nullInFirstName() {
        return new Courier("newTestCourier" + RandomStringUtils.randomAlphanumeric(3, 7), "1234", null);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}