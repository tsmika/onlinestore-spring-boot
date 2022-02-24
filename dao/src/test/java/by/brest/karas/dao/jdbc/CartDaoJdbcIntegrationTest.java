package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CustomerDao;
import by.brest.karas.model.dto.Cart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
class CartDaoJdbcIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartDaoJdbcIntegrationTest.class);
    @Autowired
    private CartDaoJdbc cartDaoJdbc;

    @Test
    public void findCartByCustomerIdWithSumTotalIntegrationTest(){
        Cart cart = cartDaoJdbc.findCartByCustomerIdWithSumTotal(1);
        assertNotNull(cart);
        assertTrue(cart.getCustomerId() == 1);
        assertTrue(cart.getCartSumTotal() == BigDecimal.valueOf(18.87));
    }


}