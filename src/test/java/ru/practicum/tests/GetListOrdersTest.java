package ru.practicum.tests;

import org.junit.Before;
import org.junit.Test;
import ru.practicum.steps.OrderSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetListOrdersTest {
    private OrderSteps orderSteps;

    @Before
    public void setUp(){
        orderSteps = new OrderSteps();
    }

    @Test
    public void getListOrdersTest() {
        orderSteps.getOrdersList()
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
