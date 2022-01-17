package by.brest.karas.model;

import org.junit.Assert;
import org.junit.Test;

public class CustomerTest {

    @Test
    public void getLoginTestConstructor() {

        Customer customer = new Customer("testLogin", "", Role.ROLE_USER, true);
        Assert.assertEquals("testLogin", customer.getLogin());

    }

    @Test
    public void getLoginTestSetter() {

        Customer customer = new Customer();
        customer.setLogin("testLogin");
        Assert.assertEquals("testLogin", customer.getLogin());
    }

}