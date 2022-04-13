package by.brest.karas.service.impl;

import by.brest.karas.dao.jdbc.CartRecordDaoJdbc;
import by.brest.karas.model.CartRecord;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CartRecordServiceImplMockTest {
    @InjectMocks
    private CartRecordDaoJdbc cartRecordDaoJdbc;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void findCartRecordsByCustomerIdTest() {
        String sql = "findCartRecordsByCustomerId";
        ReflectionTestUtils.setField(cartRecordDaoJdbc, "findCartRecordsByCustomerIdSql", sql);
        CartRecord cartRecord1 = new CartRecord();
        CartRecord cartRecord2 = new CartRecord();
        List<CartRecord> cartRecordList = Arrays.asList(cartRecord1, cartRecord2);
        ArgumentMatcher<SqlParameterSource> argumentMatcher = sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("ID");

        Mockito.when(namedParameterJdbcTemplate.query(eq(sql), argThat(argumentMatcher), ArgumentMatchers.<RowMapper<CartRecord>>any())).thenReturn(cartRecordList);
        List<CartRecord> cartRecords = cartRecordDaoJdbc.findCartRecordsByCustomerId(1);
        assertNotNull(cartRecords);
        assertFalse(cartRecords.isEmpty());
        assertEquals(2, cartRecords.size());
        assertSame(cartRecords.get(0), cartRecord1);
        assertSame(cartRecords.get(1), cartRecord2);
        assertSame(cartRecords, cartRecordList);

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), argThat(argumentMatcher), ArgumentMatchers.<RowMapper<CartRecord>>any());
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void findCartRecordsByCustomerIdAndProductIdTest() {
        String sql = "findCartRecordByCustomerIdAndProductId";
        ReflectionTestUtils.setField(cartRecordDaoJdbc, "findCartRecordByCustomerIdAndProductIdSql", sql);
        CartRecord cartRecord = new CartRecord();
        List<CartRecord> cartRecordList = Collections.singletonList(cartRecord);
        ArgumentMatcher<SqlParameterSource> argumentMatcher = sqlParameterSource ->
                Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("CUSTOMER_ID") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[1].equals("PRODUCT_ID");

        Mockito.when(namedParameterJdbcTemplate.query(eq(sql), argThat(argumentMatcher), ArgumentMatchers.<RowMapper<CartRecord>>any())).thenReturn(cartRecordList);
        List<CartRecord> cartRecords = cartRecordDaoJdbc.findCartRecordsByCustomerIdAndProductId(1, 1);
        assertNotNull(cartRecords);
        assertFalse(cartRecords.isEmpty());
        assertEquals(1, cartRecords.size());
        assertSame(cartRecords.get(0), cartRecord);
        assertSame(cartRecords, cartRecordList);

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), argThat(argumentMatcher), ArgumentMatchers.<RowMapper<CartRecord>>any());
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void createTest() {
        CartRecord cartRecord = new CartRecord();
        cartRecord.setCustomerId(1);
        cartRecord.setProductId(1);
        List<CartRecord> cartRecordList = Collections.singletonList(cartRecord);
        ReflectionTestUtils.setField(cartRecordDaoJdbc, "createSql", "create");
        ReflectionTestUtils.setField(cartRecordDaoJdbc, "updateSql", "update");
        ReflectionTestUtils.setField(cartRecordDaoJdbc, "findProductInCartRecordsByCustomerIdSql", "findProductInCartRecordsByCustomerId");

        ArgumentMatcher<MapSqlParameterSource> argumentMatcherForCheckCartRecordExists = sqlParameterSource ->
                Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("CUSTOMER_ID") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[1].equals("PRODUCT_ID");

        //Checking to update an existing cart record
        Mockito.when(namedParameterJdbcTemplate.query(eq("findProductInCartRecordsByCustomerId"),
                argThat(argumentMatcherForCheckCartRecordExists),
                ArgumentMatchers.<RowMapper<CartRecord>>any()
        )).thenReturn(cartRecordList);

        assertTrue(cartRecordDaoJdbc.isCartRecordExist(1, 1));

        ArgumentMatcher<MapSqlParameterSource> argumentMatcherForCreateCartRecord = sqlParameterSource ->
                Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("CUSTOMER_ID") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[1].equals("PRODUCT_ID") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[2].equals("QUANTITY");

        Mockito.when(namedParameterJdbcTemplate.update(eq("update"), argThat(argumentMatcherForCreateCartRecord))).thenReturn(1);
        assertEquals(1, cartRecordDaoJdbc.create(cartRecord));
        Mockito.verify(namedParameterJdbcTemplate).update(eq("update"), argThat(argumentMatcherForCreateCartRecord));

        //Checking to create a new cart record
        Mockito.when(namedParameterJdbcTemplate.query(eq("findProductInCartRecordsByCustomerId"),
                argThat(argumentMatcherForCheckCartRecordExists),
                ArgumentMatchers.<RowMapper<CartRecord>>any()
        )).thenReturn(Collections.EMPTY_LIST);
        assertFalse(cartRecordDaoJdbc.isCartRecordExist(1, 1));
        Mockito.when(namedParameterJdbcTemplate.update(eq("create"), argThat(argumentMatcherForCreateCartRecord))).thenReturn(1);
        assertEquals(1, cartRecordDaoJdbc.create(cartRecord));

        Mockito.verify(namedParameterJdbcTemplate).update(eq("create"), argThat(argumentMatcherForCreateCartRecord));
        Mockito.verify(namedParameterJdbcTemplate, times(4)).query(eq("findProductInCartRecordsByCustomerId"),
                argThat(argumentMatcherForCheckCartRecordExists),
                ArgumentMatchers.<RowMapper<CartRecord>>any()
        );

        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void deleteTest() {

        ReflectionTestUtils.setField(cartRecordDaoJdbc, "deleteSql", "delete");
        ArgumentMatcher<MapSqlParameterSource> argumentMatcher = sqlParameterSource ->
                Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("CUSTOMER_ID") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[1].equals("PRODUCT_ID");

        Mockito.when(namedParameterJdbcTemplate.update(eq("delete"), argThat(argumentMatcher))).thenReturn(1);
        assertEquals(1, cartRecordDaoJdbc.delete(1, 1));
        Mockito.verify(namedParameterJdbcTemplate).update(eq("delete"), argThat(argumentMatcher));
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);

    }
}
