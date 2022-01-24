package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CustomerDao;
import by.brest.karas.model.Customer;
import by.brest.karas.model.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
public class CustomerDaoJdbcTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDaoJdbcTest.class);
    @Autowired
    private CustomerDao customerDao;

    @Test
    public void findAllTest() {
        findAllAssertion();
    }

    @Test
    public void findByIdTest() {

        Integer customerId = findAllAssertion().get(0).getCustomerId();
        Customer expectedCustomer = customerDao.findById(customerId).get();
        Assert.assertEquals(customerId, expectedCustomer.getCustomerId());
        Assert.assertTrue(customerId.intValue() == expectedCustomer.getCustomerId().intValue());
        Assert.assertEquals(expectedCustomer, findAllAssertion().get(0));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findByIdExceptionalTest() {
        customerDao.findById(Integer.MAX_VALUE).get();
    }

    @Test
    public void createCustomerTest() {
        List<Customer> customers = findAllAssertion();

        customerDao.create(new Customer("TestCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));

        List<Customer> customersAfterAddingANewOne = customerDao.findAll();
        Assert.assertTrue(customers.size() == customersAfterAddingANewOne.size() - 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCustomerWithSameLoginTest() {
        List<Customer> customers = findAllAssertion();

        customerDao.create(new Customer("TestCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));
        customerDao.create(new Customer("TestCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));
        List<Customer> customersAfterAddingANewOne = customerDao.findAll();
        Assert.assertTrue(customers.size() == customersAfterAddingANewOne.size() - 1);
    }

    @Test
    public void updateCustomerTest() {
        List<Customer> customers = findAllAssertion();
        Customer customer = customers.get(0);
        customer.setLogin("NewLoginForTest");
        customerDao.update(customer);
        Optional<Customer> updatedCustomer = customerDao.findById(customer.getCustomerId());

        Assert.assertTrue("NewLoginForTest".equals(updatedCustomer.get().getLogin()));
    }

    @Test
    public void testLogging(){
//        LOGGER.trace("Hello trace!");
//        LOGGER.debug("Hello debug!");
//        LOGGER.info("Hello info!");
//        LOGGER.warn("Hello warn!");
//        LOGGER.error("Hello error!");
    }

    private  List<Customer> findAllAssertion(){
        List<Customer> customers = customerDao.findAll();
        Assert.assertNotNull(customers);
        Assert.assertTrue(customers.size() > 0);
        return customers;
    }
}