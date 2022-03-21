package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CustomerDao;
import by.brest.karas.model.Customer;
import by.brest.karas.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
public class CustomerDaoJdbcIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDaoJdbcIntegrationTest.class);
    @Autowired
    private CustomerDao customerDao;

//    @Test
//    public void findAllTest() {
//        findAllAssertion();
//    }
//
//    @Test
//    public void findByIdTest() {
//
//        Integer customerId = findAllAssertion().get(0).getCustomerId();
//        Customer expectedCustomer = customerDao.findById(customerId).get();
//        assertEquals(customerId, expectedCustomer.getCustomerId());
//        assertTrue(customerId.intValue() == expectedCustomer.getCustomerId().intValue());
//        assertEquals(expectedCustomer, findAllAssertion().get(0));
//    }
//
//    @Test
//    public void findByLoginTest() {
//
//        String login = findAllAssertion().get(0).getLogin();
//        Customer expectedCustomer = customerDao.findByLogin(login).get();
//        assertEquals(login, expectedCustomer.getLogin());
//        assertTrue(login == expectedCustomer.getLogin());
//        assertEquals(expectedCustomer, findAllAssertion().get(0));
//    }
//
//    @Test
//    public void findByIdExceptionalTest() {
//        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
//            customerDao.findById(Integer.MAX_VALUE).get();
//        });
//    }

//    @Test
//    public void createCustomerTest() {
//        List<Customer> customers = findAllAssertion();
//
//        customerDao.create(new Customer("TestCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));
//
//        List<Customer> customersAfterAddingANewOne = customerDao.findAll();
//        assertTrue(customers.size() == customersAfterAddingANewOne.size() - 1);
//    }
//
//    @Test
//    public void createCustomerWithSameLoginTest() {
//        List<Customer> customers = findAllAssertion();
//
//        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            customerDao.create(new Customer("TestCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));
//            customerDao.create(new Customer("TestCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));
//        });
//    }
//
//    @Test
//    public void createCustomerWithSameLoginDiffCaseTest() {
//        List<Customer> customers = findAllAssertion();
//
//        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            customerDao.create(new Customer("TestCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));
//            customerDao.create(new Customer("testCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));
//        });
//    }

//    @Test
//    public void updateCustomerTest() {
//        List<Customer> customers = findAllAssertion();
//        Customer customer = customers.get(0);
//        customer.setLogin("NewLoginForTest");
//        customerDao.update(customer);
//        Optional<Customer> updatedCustomer = customerDao.findById(customer.getCustomerId());
//
//        assertTrue("NewLoginForTest".equals(updatedCustomer.get().getLogin()));
//    }
//
//    @Test
    public void testLogging() {
//        LOGGER.trace("Hello trace!");
//        LOGGER.debug("Hello debug!");
//        LOGGER.info("Hello info!");
//        LOGGER.warn("Hello warn!");
//        LOGGER.error("Hello error!");
    }

//    private List<Customer> findAllAssertion() {
//        List<Customer> customers = customerDao.findAll();
//        assertNotNull(customers);
//        assertTrue(customers.size() > 0);
//        return customers;
//    }
}