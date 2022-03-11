package by.brest.karas.service.impl;

import by.brest.karas.dao.jdbc.CartRecordDaoJdbc;
import by.brest.karas.model.CartRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
public class CartRecordServiceImplIntegrationTest {

    @Autowired
    private CartRecordServiceImpl cartRecordService;

    @Autowired
    private CartRecordDaoJdbc cartRecordDaoJdbc;

    @Test
    void findCartRecordsByCustomerIdIntegrationTest(){
        List<CartRecord> cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        assertNotNull(cartRecords);
        assertTrue(cartRecords.size() > 0);
    }

    @Test
    void isRecordExistIntegrationTest(){
        List<CartRecord> cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        assertTrue(cartRecordDaoJdbc.isRecordExist(cartRecords.get(0).getCustomerId(), cartRecords.get(0).getProductId()));
        cartRecords = cartRecordService.findCartRecordsByCustomerId(Integer.MAX_VALUE);
        assertTrue(cartRecords.size() == 0);
    }

    @Test
    void createIntegrationTest(){
        List<CartRecord> cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        assertNotNull(cartRecords);

        CartRecord testCartRecord = new CartRecord(1, 5, 5);
        int sizeBefore = cartRecords.size();
        cartRecordDaoJdbc.create(testCartRecord);
        cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        assertNotNull(cartRecords);
        assertTrue(cartRecords.size() > 0);
        int sizeAfter = cartRecords.size();
        assertTrue(sizeAfter - sizeBefore == 1);

        testCartRecord.setQuantity(8);
        sizeBefore = cartRecords.size();
        assertTrue(cartRecords.size() > 0);
        cartRecordDaoJdbc.create(testCartRecord);
        cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        assertNotNull(cartRecords);
        assertTrue(cartRecords.size() > 0);
        sizeAfter = cartRecords.size();
        assertTrue(sizeAfter - sizeBefore == 0);

    }
}
