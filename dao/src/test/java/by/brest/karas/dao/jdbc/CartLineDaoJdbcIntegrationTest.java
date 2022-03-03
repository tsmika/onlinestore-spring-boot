package by.brest.karas.dao.jdbc;

import by.brest.karas.model.dto.CartLine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
class CartLineDaoJdbcIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartLineDaoJdbcIntegrationTest.class);
    @Autowired
    private CartLineDaoJdbc cartLineDaoJdbc;

    @Test
    public void findCartLinesByCustomerIdIntegrationTest() {
        List<CartLine> cartLines = cartLineDaoJdbc.findCartLinesByCustomerId(1, "");
        assertNotNull(cartLines);
        assertNotNull(cartLines.size() == 2);
        assertTrue(cartLines.get(0).getCustomerId() == 1);
        assertTrue(cartLineDaoJdbc.findCartLinesSumByCustomerId(1, "").equals(BigDecimal.valueOf(18.87).setScale(2)));

        cartLines = cartLineDaoJdbc.findCartLinesByCustomerId(1, "aaa");
        assertNotNull(cartLines);

        cartLines = cartLineDaoJdbc.findCartLinesByCustomerId(1, "3");
        assertNotNull(cartLines);
        assertNotNull(cartLines.size() == 1);
        assertTrue(cartLines.get(0).getCustomerId() == 1);
        assertTrue(cartLineDaoJdbc.findCartLinesSumByCustomerId(1, "3").equals(BigDecimal.valueOf(16.65).setScale(2)));
    }
}