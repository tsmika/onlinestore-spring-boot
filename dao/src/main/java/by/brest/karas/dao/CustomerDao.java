package by.brest.karas.dao;

import by.brest.karas.model.Role;
import by.brest.karas.model.Customer;

import java.util.List;

public interface CustomerDao {

    List<Customer> findAll();

    Customer findByLogin(String login);

    Customer findById(Integer id);

    List<Customer> selectCustomers(String filter);

    void save (Customer customer, Role role);

    void update(Integer id, Customer updatedCustomer);

    void delete(Integer id);
}
