package by.brest.karas.service.impl;

import by.brest.karas.dao.CustomerDao;
import by.brest.karas.model.Customer;
import by.brest.karas.service.CustomerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;

    public CustomerServiceImpl(){};

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Customer findByLogin(String login) {
        return customerDao.findByLogin(login);
    }

    @Override
    public Optional<Customer> findById(Integer customerId) {
        return customerDao.findById(customerId);
    }

    @Override
    public Integer create(Customer customer) {
        return customerDao.create(customer);
    }

    @Override
    public Integer update(Customer updatedCustomer) {
        return customerDao.update(updatedCustomer);
    }

    @Override
    public Integer delete(Integer customerId) {
        return customerDao.delete(customerId);
    }

    @Override
    public List<Customer> selectCustomers(String filter) {
        return customerDao.selectCustomers(filter);
    }
}
