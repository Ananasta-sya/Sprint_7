package ru.practicum.tests;



import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practicum.models.Order;
import ru.practicum.steps.OrderSteps;


import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTests {
    private OrderSteps orderSteps = new OrderSteps();
    private final String[] colour;
    private Response response;

    public CreateOrderTests(String[] colour) {
        this.colour = colour;
    }
    @Parameterized.Parameters
    public static Object[][] getColours() {
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"GREY", "BLACK"}},
                {new String[]{}}
        };
    }

    @Test
    public void createOrderWithColourTest() {
        Order order = new Order ("Попугай", "Картошкин", "Амазония, 54", "Сокольники", "+79224575438", "2", "10.06.2026", "нету", colour);
        response = orderSteps.createOrder(order);
        response.then().statusCode(201)
                .body("track", notNullValue());
    }


}
