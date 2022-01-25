package by.brest.karas.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

    @Test
    public void getLoginTestConstructor() {

        Customer customer = new Customer("testLogin", "", Role.ROLE_USER, true);
        assertEquals("testLogin", customer.getLogin());

    }

    @Test
    public void getLoginTestSetter() {

        Customer customer = new Customer();
        customer.setLogin("testLogin");
        assertEquals("testLogin", customer.getLogin());
    }

}