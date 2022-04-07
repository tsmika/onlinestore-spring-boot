package by.brest.karas.service.impl;

import by.brest.karas.dao.jdbc.ProductDaoJdbc;
import by.brest.karas.model.Product;
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
public class ProductServiceImplMockTest {

    @InjectMocks
    private ProductDaoJdbc productDaoJdbc;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void findAllTest() {
        String sql = "select";
        ReflectionTestUtils.setField(productDaoJdbc, "selectAllSql", sql);
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> productList = Arrays.asList(product1, product2);

        Mockito.when(namedParameterJdbcTemplate.query(eq(sql), ArgumentMatchers.<RowMapper<Product>>any())).thenReturn(productList);
        List<Product> products = productDaoJdbc.findAll();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(2, products.size());
        assertSame(products.get(0), product1);
        assertSame(products.get(1), product2);
        assertSame(products, productList);

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), ArgumentMatchers.<RowMapper<Product>>any());
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void findByIdTest() {
        Product testProduct = new Product();
        testProduct.setProductId(1);
        List<Product> products = Collections.singletonList(testProduct);
        ReflectionTestUtils.setField(productDaoJdbc, "selectByIdSql", "selectById");

        Mockito.when(namedParameterJdbcTemplate.query(
                eq("selectById"),
                argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("ID")),
                ArgumentMatchers.<RowMapper<Product>>any())).thenReturn(products);

        Product product = productDaoJdbc.findById(1);
        assertNotNull(product);
        assertSame(testProduct, product);

        Mockito.verify(namedParameterJdbcTemplate).query(eq("selectById"), ArgumentMatchers.<SqlParameterSource>any(), ArgumentMatchers.<RowMapper<Product>>any());
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void findProductsByDescriptionTest() {
        String sql = "selectByDescription";
        ReflectionTestUtils.setField(productDaoJdbc, "selectByDescriptionSql", sql);
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> productList = Arrays.asList(product1, product2);

        ArgumentMatcher<SqlParameterSource> argumentMatcher = sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("FILTER");

        Mockito.when(namedParameterJdbcTemplate.query(eq(sql), argThat(argumentMatcher), ArgumentMatchers.<RowMapper<Product>>any())).thenReturn(productList);

        List<Product> products = productDaoJdbc.findProductsByDescription("");
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(2, products.size());
        assertSame(products.get(0), product1);
        assertSame(products.get(1), product2);
        assertSame(products, productList);

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), argThat(argumentMatcher), ArgumentMatchers.<RowMapper<Product>>any());
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void createTest() {
        Product product = new Product();
        ReflectionTestUtils.setField(productDaoJdbc, "createSql", "create");
        ReflectionTestUtils.setField(productDaoJdbc, "checkShortDescriptionSql", "checkShortDescription");

        ArgumentMatcher<MapSqlParameterSource> argumentMatcher = sqlParameterSource ->
                Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("PICTURE") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[1].equals("SHORT_DESCRIPTION") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[2].equals("DETAIL_DESCRIPTION") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[3].equals("PRICE") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[4].equals("CHANGED_BY");

        Mockito.when(namedParameterJdbcTemplate.queryForObject(eq("checkShortDescription"),
                        argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("SHORT_DESCRIPTION")),
                        eq(Integer.class))).
                thenReturn(0);
        assertTrue(productDaoJdbc.isShortDescriptionUnique(product));

        Mockito.when(namedParameterJdbcTemplate.update(eq("create"), argThat(argumentMatcher))).thenReturn(1);
        assertEquals(1, productDaoJdbc.create(product));

        Mockito.verify(namedParameterJdbcTemplate, times(2)).queryForObject(eq("checkShortDescription"),
                argThat((ArgumentMatcher<SqlParameterSource>) sqlParameterSource -> Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("SHORT_DESCRIPTION")),
                eq(Integer.class));
        Mockito.verify(namedParameterJdbcTemplate).update(eq("create"), argThat(argumentMatcher));
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void updateTest() {
        Product product = new Product();
        ReflectionTestUtils.setField(productDaoJdbc, "updateSql", "update");

        ArgumentMatcher<MapSqlParameterSource> argumentMatcher = sqlParameterSource ->
                Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("PRODUCT_ID") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[1].equals("PICTURE") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[2].equals("SHORT_DESCRIPTION") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[3].equals("DETAIL_DESCRIPTION") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[4].equals("PRICE") &&
                        Objects.requireNonNull(sqlParameterSource.getParameterNames())[5].equals("CHANGED_BY");

        Mockito.when(namedParameterJdbcTemplate.update(eq("update"), argThat(argumentMatcher))).thenReturn(1);
        assertEquals(1, productDaoJdbc.update(product));
        Mockito.verify(namedParameterJdbcTemplate).update(eq("update"), argThat(argumentMatcher));
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void deleteTest() {

        ReflectionTestUtils.setField(productDaoJdbc, "deleteSql", "delete");
        ArgumentMatcher<MapSqlParameterSource> argumentMatcher = sqlParameterSource ->
                Objects.requireNonNull(sqlParameterSource.getParameterNames())[0].equals("ID");

        Mockito.when(namedParameterJdbcTemplate.update(eq("delete"), argThat(argumentMatcher))).thenReturn(1);
        assertEquals(1, productDaoJdbc.delete(1));
        Mockito.verify(namedParameterJdbcTemplate).update(eq("delete"), argThat(argumentMatcher));
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }
}
