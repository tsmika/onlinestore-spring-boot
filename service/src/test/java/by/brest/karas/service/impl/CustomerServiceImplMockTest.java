package by.brest.karas.service.impl;

import by.brest.karas.dao.jdbc.CustomerDaoJdbc;
import by.brest.karas.model.Customer;
import by.brest.karas.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplMockTest {
    @InjectMocks
    private CustomerDaoJdbc customerDaoJdbc;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Captor
    private ArgumentCaptor<RowMapper<Customer>> captor;

    @Test
    public void findAllTest() {

        String sql = "select";
        ReflectionTestUtils.setField(customerDaoJdbc, "selectSql", sql);
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customerList = Arrays.asList(customer1, customer2);

        Mockito.when(namedParameterJdbcTemplate.query(eq(sql), ArgumentMatchers.<RowMapper<Customer>>any())).thenReturn(customerList);
        List<Customer> customers = customerDaoJdbc.findAll();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(2, customers.size());
        assertSame(customers.get(0), customer1);
        assertSame(customers.get(1), customer2);

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), captor.capture());
        RowMapper<Customer> mapper = captor.getValue();
        assertNotNull(mapper);
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void findByLoginTest() {
        Customer testCustomer = new Customer();
        ReflectionTestUtils.setField(customerDaoJdbc, "findByLoginSql", "findByLogin");

        Mockito.when(namedParameterJdbcTemplate.queryForObject(eq("findByLogin"),
                argThat(new ArgumentMatcher<SqlParameterSource>() {
                    @Override
                    public boolean matches(SqlParameterSource sqlParameterSource) {
                        return sqlParameterSource.getParameterNames()[0].equals("CUSTOMER_LOGIN");
                    }
                })
                , ArgumentMatchers.<RowMapper<Customer>>any())).thenReturn(testCustomer);

//        Mockito.when(namedParameterJdbcTemplate.queryForObject(eq("findByLogin"),
//                        argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> sqlParameterSource.getParameterNames()[0].equals("CUSTOMER_LOGIN"))
//                        , ArgumentMatchers.<RowMapper<Customer>>any())).thenReturn(testCustomer);

//        Mockito.when(namedParameterJdbcTemplate.queryForObject(eq("findByLogin"),
//                argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("CUSTOMER_LOGIN"))
//                , ArgumentMatchers.<RowMapper<Customer>>any())).thenReturn(testCustomer);

        Customer customer = customerDaoJdbc.findByLogin("").get();
        assertNotNull(customer);
        assertSame(testCustomer, customer);

        Mockito.verify(namedParameterJdbcTemplate).queryForObject(eq("findByLogin"), ArgumentMatchers.<SqlParameterSource>any(), ArgumentMatchers.<RowMapper<Customer>>any());
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void findByIdTest() {
        Customer testCustomer = new Customer();
        testCustomer.setCustomerId(1);
        ReflectionTestUtils.setField(customerDaoJdbc, "findByIdSql", "findById");

        Mockito.when(namedParameterJdbcTemplate.queryForObject(
                eq("findById"),
                argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("CUSTOMER_ID")),
                ArgumentMatchers.<RowMapper<Customer>>any())).thenReturn(testCustomer);

        Customer customer = customerDaoJdbc.findById(1).get();
        assertNotNull(customer);
        assertSame(testCustomer, customer);

        Mockito.verify(namedParameterJdbcTemplate).queryForObject(eq("findById"), ArgumentMatchers.<SqlParameterSource>any(), ArgumentMatchers.<RowMapper<Customer>>any());
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void searchCustomersByLoginTest() {
        String sql = "searchCustomersByLogin";
        ReflectionTestUtils.setField(customerDaoJdbc, "searchCustomersByLoginSql", sql);
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customerList = Arrays.asList(customer1, customer2);

        ArgumentMatcher<SqlParameterSource> argumentMatcher = sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("FILTER");

        Mockito.when(namedParameterJdbcTemplate.query(eq(sql), argThat(argumentMatcher), ArgumentMatchers.<RowMapper<Customer>>any())).thenReturn(customerList);

        List<Customer> customers = customerDaoJdbc.searchCustomersByLogin("");
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(2, customers.size());
        assertSame(customers.get(0), customer1);
        assertSame(customers.get(1), customer2);

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), argThat(argumentMatcher), ArgumentMatchers.<RowMapper<Customer>>any());
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void createTest() {

        Customer customer = new Customer("testLogin", "testPassword", Role.ROLE_USER, true);
        ReflectionTestUtils.setField(customerDaoJdbc, "createSql", "create");
        ReflectionTestUtils.setField(customerDaoJdbc, "checkLoginSql", "checkLogin");

        ArgumentMatcher<MapSqlParameterSource> argumentMatcher = sqlParameterSource ->
                Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("LOGIN") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[1].equals("PASSWORD") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[2].equals("ROLE") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[3].equals("IS_ACTUAL");

        Mockito.when(namedParameterJdbcTemplate.queryForObject(eq("checkLogin"),
                        argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("LOGIN")),
                        eq(Integer.class))).
                thenReturn(0);
        assertTrue(customerDaoJdbc.isLoginUnique(customer));

        Mockito.when(namedParameterJdbcTemplate.update(eq("create"), argThat(argumentMatcher))).thenReturn(1);
        assertEquals(1, customerDaoJdbc.create(customer));

        Mockito.verify(namedParameterJdbcTemplate, times(2)).queryForObject(eq("checkLogin"),
                argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("LOGIN")),
                eq(Integer.class));
        Mockito.verify(namedParameterJdbcTemplate).update(eq("create"), argThat(argumentMatcher));
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void updateTest() {
        Customer customer = new Customer("testLogin", "testPassword", Role.ROLE_USER, true);
        ReflectionTestUtils.setField(customerDaoJdbc, "updateSql", "update");

        ArgumentMatcher<MapSqlParameterSource> argumentMatcher = sqlParameterSource ->
                Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("CUSTOMER_ID") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[1].equals("LOGIN") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[2].equals("PASSWORD") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[3].equals("ROLE") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[4].equals("IS_ACTUAL");

        Mockito.when(namedParameterJdbcTemplate.update(eq("update"), argThat(argumentMatcher))).thenReturn(1);
        assertEquals(1, customerDaoJdbc.update(customer));
        Mockito.verify(namedParameterJdbcTemplate).update(eq("update"), argThat(argumentMatcher));
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void deleteTest() {
        Customer testCustomer = new Customer();
        testCustomer.setCustomerId(1);
        testCustomer.setRole(Role.ROLE_USER);
        ReflectionTestUtils.setField(customerDaoJdbc, "findByIdSql", "findById");

        Mockito.when(namedParameterJdbcTemplate.queryForObject(
                eq("findById"),
                argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("CUSTOMER_ID")),
                ArgumentMatchers.<RowMapper<Customer>>any())).thenReturn(testCustomer);

        Customer customer = customerDaoJdbc.findById(1).get();
        assertNotNull(customer);
        assertSame(testCustomer, customer);

        Mockito.verify(namedParameterJdbcTemplate).queryForObject(eq("findById"), ArgumentMatchers.<SqlParameterSource>any(), ArgumentMatchers.<RowMapper<Customer>>any());
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);

        ReflectionTestUtils.setField(customerDaoJdbc, "deleteAdminSql", "deleteAdmin");
        ReflectionTestUtils.setField(customerDaoJdbc, "deleteCustomerSql", "deleteCustomer");

        Mockito.when(namedParameterJdbcTemplate.update(
                        eq("deleteCustomer"),
                        argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("ID")))).
                thenReturn(1);

        Integer result = customerDaoJdbc.delete(1);
        assertNotNull(result);
        assertEquals(1, result);


        Mockito.verify(namedParameterJdbcTemplate).update(eq("deleteCustomer"),
                argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("ID")));


        customer = customerDaoJdbc.findById(1).get();
        assertNotNull(customer);
        assertSame(testCustomer, customer);
        testCustomer.setRole(Role.ROLE_ADMIN);
        Mockito.when(namedParameterJdbcTemplate.update(
                        eq("deleteAdmin"),
                        argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("ID")))).
                thenReturn(1);

        result = customerDaoJdbc.delete(1);
        assertNotNull(result);
        assertEquals(1, result);

        Mockito.verify(namedParameterJdbcTemplate).update(eq("deleteAdmin"),
                argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("ID")));

        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }
}
