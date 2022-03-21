package by.brest.karas.dao;

import by.brest.karas.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> findAll();

    Optional<Customer> findById(Integer customerId);

    Optional<Customer> findByLogin(String login);

    Integer create (Customer customer);

    Integer update(Customer updatedCustomer);

    Integer delete(Integer customerId);

    List<Customer> searchCustomersByLogin(String filter);
}
