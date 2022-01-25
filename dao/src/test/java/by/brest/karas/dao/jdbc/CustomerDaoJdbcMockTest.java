package by.brest.karas.dao.jdbc;

import by.brest.karas.model.Customer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

//@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
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

        Customer customer = new Customer();
        List<Customer> customerList = Collections.singletonList(customer);

        Mockito.when(namedParameterJdbcTemplate.query(any(), ArgumentMatchers.<RowMapper<Customer>>any())).thenReturn(customerList);
        List<Customer> customers = customerDaoJdbc.findAll();
        Assert.assertNotNull(customers);
        Assert.assertFalse(customers.isEmpty());
        Assert.assertSame(customers.get(0), customer);

        Mockito.verify(namedParameterJdbcTemplate).query(eq("select"), captor.capture());
        RowMapper<Customer> mapper = captor.getValue();
        Assert.assertNotNull(mapper);
//        Mockito.verify(namedParameterJdbcTemplate).query(eq("select"), any(RowMapper.class)); // 2
//        Mockito.verify(namedParameterJdbcTemplate).query(any(), ArgumentMatchers.<RowMapper<Customer>>any()); // 1
//        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate); // 1

    }
}