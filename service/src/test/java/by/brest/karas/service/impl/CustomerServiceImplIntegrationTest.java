package by.brest.karas.service.impl;

import by.brest.karas.model.Customer;
import by.brest.karas.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
public class CustomerServiceImplIntegrationTest {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private CartRecordServiceImpl cartRecordService;

    @Test
    void findAllIntegrationTest() {
        findAllAssertion();
    }

    private List<Customer> findAllAssertion() {
        List<Customer> customers = customerService.findAll();
        assertNotNull(customers);
        assertTrue(customers.size() > 0);
        return customers;
    }

    @Test
    public void findByIdIntegrationTest() {
        Integer customerId = findAllAssertion().get(0).getCustomerId();
        Customer expectedCustomer = customerService.findById(customerId).get();
        assertEquals(customerId, expectedCustomer.getCustomerId());
        assertEquals(customerId.intValue(), expectedCustomer.getCustomerId().intValue());
        assertEquals(expectedCustomer, findAllAssertion().get(0));
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            customerService.findById(Integer.MAX_VALUE).get();
        });
    }

    @Test
    public void findByLoginIntegrationTest() {
        Customer customer = findAllAssertion().get(2);
        Customer expectedCustomer = customerService.findByLogin("customer1").get();
        assertEquals(expectedCustomer, customer);
    }

    @Test
    void searchCustomersByLoginIntegrationTest() {
        List<Customer> customers = findAllAssertion();
        String filter = "{]@*";
        Customer testCustomer = new Customer(filter, "password", Role.ROLE_ADMIN, true);
        customerService.create(testCustomer);
        customers = findAllAssertion();
        customers = customerService.searchCustomersByLogin(filter);
        assertNotNull(customers);
        assertEquals(1, customers.size());
    }

    @Test
    public void findByIdExceptionalIntegrationTest() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            customerService.findById(Integer.MAX_VALUE).get();
        });
    }

    @Test
    public void createCustomerIntegrationTest() {
        List<Customer> customers = findAllAssertion();
        customerService.create(new Customer("TestCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));
        List<Customer> customersAfterAddingANewOne = customerService.findAll();
        assertEquals(customers.size(), customersAfterAddingANewOne.size() - 1);
    }

    @Test
    public void createCustomerWithSameLoginIntegrationTest() {
        List<Customer> customers = findAllAssertion();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            customerService.create(new Customer("TestCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));
            customerService.create(new Customer("TestCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));
        });
    }

    @Test
    public void createCustomerWithSameLoginDiffCaseIntegrationTest() {
        List<Customer> customers = findAllAssertion();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            customerService.create(new Customer("TestCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));
            customerService.create(new Customer("testCustomerLogin", "TestCustomerPassword", Role.ROLE_USER, true));
        });
    }

    @Test
    public void updateCustomerIntegrationTest() {
        List<Customer> customers = findAllAssertion();
        Customer customer = customers.get(0);
        customer.setLogin("NewLoginForTest");
        customerService.update(customer);
        Optional<Customer> updatedCustomer = customerService.findById(customer.getCustomerId());

        assertSame("NewLoginForTest", updatedCustomer.get().getLogin());
    }

    @Test
    public void deleteIntegrationTest() {
        List<Customer> customers = findAllAssertion();
        int sizeBefore = customers.size();
        assertEquals(4, sizeBefore);
        int cartRecordSizeBefore = cartRecordService.findCartRecordsByCustomerId(customers.get(2).getCustomerId()).size();
        assertEquals(3, cartRecordSizeBefore);
        customerService.delete(3);
        int cartRecordSizeAfter = cartRecordService.findCartRecordsByCustomerId(customers.get(2).getCustomerId()).size();
        assertEquals(0, cartRecordSizeAfter);
        customers = findAllAssertion();
        int sizeAfter = customers.size();
        assertEquals(sizeBefore - 1, sizeAfter);

        customers = findAllAssertion();
        assertTrue((boolean) customers.get(0).getIsActual());
        sizeBefore = customers.size();
        cartRecordSizeBefore = cartRecordService.findCartRecordsByCustomerId(customers.get(0).getCustomerId()).size();
        assertEquals(2, cartRecordSizeBefore);
        customerService.delete(1);
        customers = findAllAssertion();
        sizeAfter = customers.size();
        cartRecordSizeAfter = cartRecordService.findCartRecordsByCustomerId(customers.get(0).getCustomerId()).size();
        assertEquals(sizeBefore, sizeAfter);
        assertFalse((boolean) customers.get(0).getIsActual());
        assertEquals(0, cartRecordSizeAfter);

    }
}
