package by.brest.karas.service.impl;

import by.brest.karas.dao.jdbc.CartRecordDaoJdbc;
import by.brest.karas.model.CartRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
public class CartRecordServiceImplIntegrationTest {

    @Autowired
    private CartRecordServiceImpl cartRecordService;

    @Autowired
    private CartRecordDaoJdbc cartRecordDaoJdbc;

    @Test
    public void findCartRecordsByCustomerIdIntegrationTest() {
        List<CartRecord> cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        assertNotNull(cartRecords);
        assertTrue(cartRecords.size() > 0);
    }

    @Test
    public void findCartRecordsByCustomerIdAndProductIdIntegrationTest() {
        List<CartRecord> cartRecords = cartRecordService.findCartRecordsByCustomerIdAndProductId(1, 2);
        assertNotNull(cartRecords);
        assertEquals(1, cartRecords.size());
        assertEquals(2, (int) cartRecords.get(0).getProductId());
        assertEquals(1, (int) cartRecords.get(0).getCustomerId());
        assertEquals(1, (int) cartRecords.get(0).getQuantity());
        cartRecords = cartRecordService.findCartRecordsByCustomerIdAndProductId(1, Integer.MAX_VALUE);
        assertEquals(0, cartRecords.size());
        cartRecords = cartRecordService.findCartRecordsByCustomerIdAndProductId(Integer.MAX_VALUE, 1);
        assertEquals(0, cartRecords.size());
    }

    @Test
    void isCartRecordExistIntegrationTest() {
        List<CartRecord> cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        assertTrue(cartRecordDaoJdbc.isCartRecordExist(cartRecords.get(0).getCustomerId(), cartRecords.get(0).getProductId()));
        assertFalse(cartRecordDaoJdbc.isCartRecordExist(cartRecords.get(0).getCustomerId(), Integer.MAX_VALUE));
        cartRecords = cartRecordService.findCartRecordsByCustomerId(Integer.MAX_VALUE);
        assertEquals(0, cartRecords.size());
    }

    @Test
    void createIntegrationTest() {
        List<CartRecord> cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        assertNotNull(cartRecords);

        CartRecord testCartRecord = new CartRecord(1, 5, 5);
        int sizeBefore = cartRecords.size();
        cartRecordDaoJdbc.create(testCartRecord);
        cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        assertNotNull(cartRecords);
        assertTrue(cartRecords.size() > 0);
        int sizeAfter = cartRecords.size();
        assertEquals(1, sizeAfter - sizeBefore);

        testCartRecord.setQuantity(8);
        sizeBefore = cartRecords.size();
        assertTrue(cartRecords.size() > 0);
        cartRecordDaoJdbc.create(testCartRecord);
        cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        assertNotNull(cartRecords);
        assertTrue(cartRecords.size() > 0);
        sizeAfter = cartRecords.size();
        assertEquals(0, sizeAfter - sizeBefore);
    }

    @Test
    public void deleteIntegrationTest() {
        List<CartRecord> cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        assertNotNull(cartRecords);
        int sizeBefore = cartRecords.size();
        assertEquals(2, sizeBefore);
        cartRecordService.delete(1, cartRecords.get(0).getProductId());
        cartRecords = cartRecordService.findCartRecordsByCustomerId(1);
        int sizeAfter = cartRecords.size();
        assertEquals(sizeBefore - 1, sizeAfter);
    }
}
