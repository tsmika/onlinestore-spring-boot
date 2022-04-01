package by.brest.karas.dao.jdbc;

import by.brest.karas.model.Customer;
import by.brest.karas.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class CustomerDaoJdbcMockTest {

//    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDaoJdbcMockTest.class);

    @InjectMocks
    private CustomerDaoJdbc customerDaoJdbc;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Captor
    private ArgumentCaptor<RowMapper<Customer>> captor;

    @Test
    public void findAllTest() {

        ReflectionTestUtils.setField(customerDaoJdbc, "selectSql", "select");
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customerList = Arrays.asList(customer1, customer2);
//        List<Customer> customerList = Collections.singletonList(customer);

        Mockito.when(namedParameterJdbcTemplate.query(any(), ArgumentMatchers.<RowMapper<Customer>>any())).thenReturn(customerList);
        List<Customer> customers = customerDaoJdbc.findAll();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(2, customers.size());
        assertSame(customers.get(0), customer1);
        assertSame(customers.get(1), customer2);

        Mockito.verify(namedParameterJdbcTemplate).query(eq("select"), captor.capture());
        RowMapper<Customer> mapper = captor.getValue();
        assertNotNull(mapper);
        Mockito.verify(namedParameterJdbcTemplate).query(eq("select"), any(RowMapper.class)); // 2
//        Mockito.verify(namedParameterJdbcTemplate).query(any(), ArgumentMatchers.<RowMapper<Customer>>any()); // 1
//        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate); // 1
    }

//    @Test
//    public void createTest() {
//        Customer customer = new Customer();
//        customer.setCustomerId(Integer.MAX_VALUE);
//        Mockito.when(namedParameterJdbcTemplate.queryForObject(any(String.class), any(MapSqlParameterSource.class), eq(Integer.class))).thenReturn(5);
//        Mockito.when(namedParameterJdbcTemplate.queryForObject(any(String.class), any(MapSqlParameterSource.class), eq(Integer.class))).thenReturn(0);
//        customerDaoJdbc.create(customer);
//    }
}

















