package by.brest.karas.service.impl;

import by.brest.karas.model.dto.CartLine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
public class CartLineServiceImplIntegrationTest {

    @Autowired
    private CartLineServiceImpl cartService;

    @Test
    public void findCartLinesByCustomerIdIntegrationTest() {

//        CartLine cart = cartService.findCartByCustomerId(1);
//        assertNotNull(cart);
//        assertNotNull(cart.getCartRecords());
//        assertNotNull(cart.getCartSumTotal());
//        assertTrue(cart.getCustomerId() == 1);
//        assertTrue(cart.getCartRecords().size() == 2);
//        assertTrue(cart.getCartSumTotal().equals(BigDecimal.valueOf(18.87).setScale(2)));
    }
}
