package ru.practicum.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.models.Courier;
import ru.practicum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class LogInCourierTests {
    private Courier courier;
    CourierSteps courierSteps = new CourierSteps();
    private String login;
    private String password;
    private String firstName;

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier = new Courier(login, password, firstName);
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
        courier.setFirstName(RandomStringUtils.randomAlphabetic(10));
        courierSteps.createCourier(courier);
    }

    @Test
    public void logInCourierTest() {
        courierSteps.loginCourier(courier)
                .statusCode(200)
                .body("id", notNullValue());
    }
    @Test
    public void logInWithoutLoginCourierTest() {
        courier.setLogin(null);
        courierSteps.loginCourier(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    public void logInWithoutPasswordCourierTest() {
        courier.setPassword(null);
        courierSteps.loginCourier(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    public void logInWithInvalidPasswordCourierTest() {
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
        courierSteps.loginCourier(courier)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    public void logInWithInvalidLoginCourierTest() {
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courierSteps.loginCourier(courier)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        Response response = courierSteps.loginCourier(courier).extract().response();

        if (response.getStatusCode() == 200) {
            Integer id = response.path("id");
            courierSteps.deleteCourier(id);
        }
    }
}