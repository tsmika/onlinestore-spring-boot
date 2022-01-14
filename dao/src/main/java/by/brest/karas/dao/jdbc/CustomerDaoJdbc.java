package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CustomerDao;
import by.brest.karas.model.Role;
import by.brest.karas.model.Customer;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDaoJdbc implements CustomerDao {

    private static final String SQL_GET_ALL_USERS = "SELECT  U.CUSTOMER_ID, U.CUSTOMER_LOGIN, U.CUSTOMER_PASSWORD, U.CUSTOMER_ROLE, U.CUSTOMER_IS_EXISTED FROM CUSTOMER AS U ORDER BY U.CUSTOMER_LOGIN";

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return namedParameterJdbcTemplate.query(SQL_GET_ALL_USERS, new CustomerRowMapper());
    }

    private class CustomerRowMapper implements org.springframework.jdbc.core.RowMapper<Customer> {

        @Override
        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {

            Customer customer = new Customer();
            customer.setUserId(resultSet.getInt("CUSTOMER_ID"));
            customer.setLogin(resultSet.getString("CUSTOMER_LOGIN"));
            customer.setPassword(resultSet.getString("CUSTOMER_PASSWORD"));
            customer.setRole(Role.valueOf(resultSet.getString("CUSTOMER_ROLE")));
            customer.setExisted(resultSet.getBoolean("CUSTOMER_IS_EXISTED"));
            return customer;
        }
    }

    @Override
    public Customer findByLogin(String login) {
        return null;
    }

    @Override
    public Customer findById(Integer id) {
        return null;
    }

    @Override
    public List<Customer> selectCustomers(String filter) {
        return null;
    }

    @Override
    public void save(Customer customer, Role role) {

    }

    @Override
    public void update(Integer id, Customer updatedCustomer) {

    }

    @Override
    public void delete(Integer id) {

    }
}
