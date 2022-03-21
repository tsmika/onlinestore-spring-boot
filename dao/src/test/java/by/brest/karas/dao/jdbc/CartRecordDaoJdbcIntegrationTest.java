package by.brest.karas.dao.jdbc;

import by.brest.karas.model.CartRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
public class CartRecordDaoJdbcIntegrationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartRecordDaoJdbcIntegrationTest.class);
    @Autowired
    private CartRecordDaoJdbc cartRecordDaoJdbc;

    @Test
    public void findFilteredCartRecordsByCustomerIdIntegrationTest() {
        List<CartRecord> cartRecords = cartRecordDaoJdbc.findFilteredCartRecordstByCustomerId(1, "2");
        assertNotNull(cartRecords);
        assertTrue(cartRecords.size() > 0);
        assertTrue(cartRecords.get(0).getProductId() == 2);
    }
}

