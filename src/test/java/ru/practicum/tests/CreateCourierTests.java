package ru.practicum.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.models.Courier;
import ru.practicum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.equalTo;


public class CreateCourierTests {
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
    }
    @Test
    public void createCourierTest() {
        courierSteps.createCourier(courier)
                .statusCode(201)
                .body("ok", Matchers.is (true));
    }

    @Test
    public void cannotCreateTwoSameCouriersTest() {
        courierSteps.createCourier(courier);
        courierSteps.createCourier(courier)
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
    @Test
    public void cannotCreateCourierWithoutLoginTest() {
        courier.setLogin(null);
        courierSteps.createCourier(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    public void cannotCreateCourierWithoutPasswordTest() {
        courier.setPassword(null);
        courierSteps.createCourier(courier)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
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