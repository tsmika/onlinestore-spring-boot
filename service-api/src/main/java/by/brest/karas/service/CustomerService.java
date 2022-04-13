package by.brest.karas.service;

import by.brest.karas.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> findAll();

    Optional<Customer> findByLogin(String login);

    Optional<Customer> findById(Integer customerId);

    List<Customer> searchCustomersByLogin(String filter);

    Integer create(Customer customer);

    Integer update(Customer updatedCustomer);

    Integer delete(Integer customerId);
}
