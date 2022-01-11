package by.brest.karas.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void getLoginTestConstructor() {

        User user = new User(1, "testLogin", "", Role.ROLE_USER);
        Assert.assertEquals("testLogin", user.getLogin());

    }

    @Test
    public void getLoginTestSetter() {

        User user = new User();
        user.setLogin("testLogin");
        Assert.assertEquals("testLogin", user.getLogin());
    }

}