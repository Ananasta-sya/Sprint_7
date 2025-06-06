package ru.practicum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.practicum.models.Order;

import static io.restassured.RestAssured.given;
import static ru.practicum.steps.CourierSteps.URL;

public class OrderSteps {
    private static final String ORDERS_API = "/api/v1/orders";

    public Response createOrder(Order order) {
        return given()
                .baseUri(URL)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ORDERS_API);
    }
    public Response getOrdersList() {
        return given()
                .baseUri(URL)
                .contentType(ContentType.JSON)
                .get(ORDERS_API);
    }

}
