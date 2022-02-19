package by.brest.karas.service.impl;

import by.brest.karas.dao.jdbc.CustomerDaoJdbc;
import by.brest.karas.model.Customer;
import by.brest.karas.model.Product;
import by.brest.karas.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
public class CustomerServiceImplIntegrationTest {
    @Autowired
    private CustomerDaoJdbc customerDao;

    @Test
    void getAllCustomersTest() {
        findAllAssertion();
    }

    private List<Customer> findAllAssertion() {
        List<Customer> customers = customerDao.findAll();
        assertNotNull(customers);
        assertTrue(customers.size() > 0);
        return customers;
    }

    @Test
    void searchCustomersByLoginIntegrationTest() {
        List<Customer> customers = findAllAssertion();
        String filter = "{]@*";
        Customer testCustomer = new Customer(filter, "password", Role.ROLE_ADMIN, true);
        customerDao.create(testCustomer);
        customers = findAllAssertion();
        customers = customerDao.searchCustomersByLogin(filter);
        assertNotNull(customers);
        assertTrue(customers.size() == 1);

    }
}
