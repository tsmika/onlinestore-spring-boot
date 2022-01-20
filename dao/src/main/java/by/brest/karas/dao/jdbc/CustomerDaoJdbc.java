package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CustomerDao;
import by.brest.karas.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("SqlResolve")
public class CustomerDaoJdbc implements CustomerDao {


    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Customer.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDaoJdbc.class);

    @SuppressWarnings("SqlResolve")
    private static final String SQL_GET_ALL_CUSTOMERS =
            "SELECT  C.CUSTOMER_ID, C.LOGIN, C.PASSWORD, C.ROLE, C.IS_ACTUAL FROM CUSTOMER AS C ORDER BY C.LOGIN";

    private static final String SQL_GET_CUSTOMER_BY_ID =
            "SELECT  C.CUSTOMER_ID, C.LOGIN, C.PASSWORD, C.ROLE, C.IS_ACTUAL FROM CUSTOMER AS C WHERE C.CUSTOMER_ID = :CUSTOMER_ID";

    private static final String SQL_CREATE_CUSTOMER =
            "INSERT INTO CUSTOMER(LOGIN, PASSWORD, ROLE, IS_ACTUAL)  VALUES (:LOGIN, :PASSWORD, :ROLE, :IS_ACTUAL)";

    private static final String SQL_CHECK_CUSTOMER_LOGIN =
            "SELECT COUNT(LOGIN) FROM CUSTOMER WHERE lower(LOGIN) = lower(:LOGIN)";

    private static final String SQL_UPDATE_CUSTOMER =
            "UPDATE CUSTOMER SET LOGIN = :LOGIN, PASSWORD = :PASSWORD, ROLE = :ROLE, IS_ACTUAL = :IS_ACTUAL WHERE CUSTOMER_ID = :CUSTOMER_ID";

    public CustomerDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        LOGGER.debug("Find all customers");
        return namedParameterJdbcTemplate.query(SQL_GET_ALL_CUSTOMERS, rowMapper);
    }

    @Override
    public Optional<Customer> findById(Integer customerId) {
        LOGGER.debug("Find customer by id: {}", customerId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("CUSTOMER_ID", customerId);
        return Optional.ofNullable((Customer) namedParameterJdbcTemplate.queryForObject(SQL_GET_CUSTOMER_BY_ID, sqlParameterSource, rowMapper));
    }

    @Override
    public Customer findByLogin(String login) {
        return null;
    }

    @Override
    public Integer create(Customer customer) {
        LOGGER.debug("Create customer: {}", customer);

        if(!isLoginUnique(customer)){
            LOGGER.warn("User with the same login already exists in DB: {}", customer);
            throw new IllegalArgumentException("User with the same login already exists in DB");
        }
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource
                .addValue("LOGIN", customer.getLogin())
                .addValue("PASSWORD", customer.getPassword())
                .addValue("ROLE", customer.getRole().toString())
                .addValue("IS_ACTUAL", customer.getIsActual());
        return namedParameterJdbcTemplate.update(SQL_CREATE_CUSTOMER, mapSqlParameterSource);
    }

    private boolean isLoginUnique(Customer customer){
        return namedParameterJdbcTemplate.queryForObject(SQL_CHECK_CUSTOMER_LOGIN, new MapSqlParameterSource("LOGIN", customer.getLogin()), Integer.class) == 0;
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

        return namedParameterJdbcTemplate.update(SQL_UPDATE_CUSTOMER, mapSqlParameterSource);
    }


    @Override
    public Integer delete(Integer customerId) {
        return 0;
    }

    @Override
    public List<Customer> selectCustomers(String filter) {
        return null;
    }
}

//        return namedParameterJdbcTemplate.query(SQL_GET_ALL_CUSTOMERS, new CustomerRowMapper());
//    private class CustomerRowMapper implements org.springframework.jdbc.core.RowMapper<Customer> {
//        @Override
//        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
//
//            Customer customer = new Customer();
//            customer.setCustomerId(resultSet.getInt("CUSTOMER_ID"));
//            customer.setLogin(resultSet.getString("LOGIN"));
//            customer.setPassword(resultSet.getString("PASSWORD"));
//            customer.setRole(Role.valueOf(resultSet.getString("ROLE")));
//            customer.setIsExisted(resultSet.getBoolean("IS_EXISTED"));
//            return customer;
//        }
//    }


