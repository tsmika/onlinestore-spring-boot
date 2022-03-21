package by.brest.karas.service.impl;

import by.brest.karas.model.CartRecord;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertTrue(customerId.intValue() == expectedCustomer.getCustomerId().intValue());
        assertEquals(expectedCustomer, findAllAssertion().get(0));
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
        assertTrue(customers.size() == 1);
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
        assertTrue(customers.size() == customersAfterAddingANewOne.size() - 1);
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

        assertTrue("NewLoginForTest".equals(updatedCustomer.get().getLogin()));
    }

    @Test
    public void deleteIntegrationTest() {
        List<Customer> customers = findAllAssertion();
        int sizeBefore = customers.size();
        assertTrue(sizeBefore == 4);
        int cartRecordSizeBefore = cartRecordService.findCartRecordsByCustomerId(customers.get(2).getCustomerId()).size();
        assertTrue(cartRecordSizeBefore == 3);
        customerService.delete(3);
        int cartRecordSizeAfter = cartRecordService.findCartRecordsByCustomerId(customers.get(2).getCustomerId()).size();
        assertTrue(cartRecordSizeAfter == 0);
        customers = findAllAssertion();
        int sizeAfter = customers.size();
        assertTrue(sizeBefore - 1 == sizeAfter);

        customers = findAllAssertion();
        assertTrue(customers.get(0).getIsActual() == true);
        sizeBefore = customers.size();
        cartRecordSizeBefore = cartRecordService.findCartRecordsByCustomerId(customers.get(0).getCustomerId()).size();
        assertTrue(cartRecordSizeBefore == 2);
        customerService.delete(1);
        customers = findAllAssertion();
        sizeAfter = customers.size();
        cartRecordSizeAfter = cartRecordService.findCartRecordsByCustomerId(customers.get(0).getCustomerId()).size();
        assertTrue(sizeBefore == sizeAfter);
        assertTrue(customers.get(0).getIsActual() == false);
        assertTrue(cartRecordSizeAfter == 0);

    }
}
