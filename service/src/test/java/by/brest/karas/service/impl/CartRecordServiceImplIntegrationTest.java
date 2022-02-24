package by.brest.karas.service.impl;

import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
public class CartRecordServiceImplIntegrationTest {

    @Autowired
    private CartRecordServiceImpl cartRecordService;

    @Test
    void findCartRecordsByCustomerIdIntegrationTest(){
        List<CartRecord> cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        assertNotNull(cartRecords);
        assertTrue(cartRecords.size() > 0);
    }
}
