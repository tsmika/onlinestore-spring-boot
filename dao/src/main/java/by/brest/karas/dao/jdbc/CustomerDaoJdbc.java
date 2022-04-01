package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CustomerDao;
import by.brest.karas.model.Customer;
import by.brest.karas.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;
import java.util.Optional;

public class CustomerDaoJdbc implements CustomerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDaoJdbc.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Customer> rowMapper = BeanPropertyRowMapper.newInstance(Customer.class);

    @Value("${customer.select}")
    private String selectSql;

    @Value("${customer.findById}")
    private String findByIdSql;

    @Value("${customer.findByLogin}")
    private String findByLoginSql;

    @Value("${customer.create}")
    private String createSql;

    @Value("${customer.checkLogin}")
    private String checkLoginSql;

    @Value("${customer.update}")
    private String updateSql;

    @Value("${customer.searchCustomersByLogin}")
    private String searchCustomersByLoginSql;

    @Value("${customer.deleteAdmin}")
    private String deleteAdminSql;

    @Value("${customer.deleteCustomer}")
    private String deleteCustomerSql;


    public CustomerDaoJdbc() {
    }

    public CustomerDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        LOGGER.debug("Find all customers");
        return namedParameterJdbcTemplate.query(selectSql, rowMapper);
    }

    @Override
    public Optional<Customer> findByLogin(String login) {
        LOGGER.debug("Find customer by login: {}", login);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("CUSTOMER_LOGIN", login);
        return Optional.ofNullable((Customer) namedParameterJdbcTemplate.queryForObject(findByLoginSql, sqlParameterSource, rowMapper));
    }

    @Override
    public Optional<Customer> findById(Integer customerId) {
        LOGGER.debug("Find customer by id: {}", customerId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("CUSTOMER_ID", customerId);
        return Optional.ofNullable((Customer) namedParameterJdbcTemplate.queryForObject(findByIdSql, sqlParameterSource, rowMapper));
    }

    @Override
    public List<Customer> searchCustomersByLogin(String filter) {
        LOGGER.debug("Search customers by login");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("FILTER", "%" + filter + "%");
        return namedParameterJdbcTemplate.query(searchCustomersByLoginSql, sqlParameterSource, rowMapper);
    }

    @Override
    public Integer create(Customer customer) {
        LOGGER.debug("Create customer: {}", customer);

        if (!isLoginUnique(customer)) {
            LOGGER.warn("User with the same login already exists in DB: {}", customer);
            throw new IllegalArgumentException("User with the same login already exists in DB");
        }
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource
                .addValue("LOGIN", customer.getLogin())
                .addValue("PASSWORD", customer.getPassword())
                .addValue("ROLE", customer.getRole().toString())
                .addValue("IS_ACTUAL", customer.getIsActual());
        return namedParameterJdbcTemplate.update(createSql, mapSqlParameterSource);
    }

    private boolean isLoginUnique(Customer customer) {
        return namedParameterJdbcTemplate.queryForObject(checkLoginSql, new MapSqlParameterSource("LOGIN", customer.getLogin()), Integer.class) == 0;
    }

    @Override
    public Integer update(Customer customer) {
        LOGGER.debug("Update customer: {}", customer);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource
                .addValue("CUSTOMER_ID", customer.getCustomerId())
                .addValue("LOGIN", customer.getLogin())
                .addValue("PASSWORD", customer.getPassword())
                .addValue("ROLE", customer.getRole().toString())
                .addValue("IS_ACTUAL", customer.getIsActual());

        return namedParameterJdbcTemplate.update(updateSql, mapSqlParameterSource);
    }


    @Override
    public Integer delete(Integer customerId) {

        LOGGER.debug("Delete customer: {}", customerId);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource().addValue("ID", customerId);

        return findById(customerId).get().getRole() == Role.ROLE_ADMIN ?
                namedParameterJdbcTemplate.update(deleteAdminSql, mapSqlParameterSource) :
                namedParameterJdbcTemplate.update(deleteCustomerSql, mapSqlParameterSource);

    }
}

