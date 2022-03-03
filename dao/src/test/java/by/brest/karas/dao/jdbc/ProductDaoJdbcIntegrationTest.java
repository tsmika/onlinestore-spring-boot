package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.ProductDao;
import by.brest.karas.model.Customer;
import by.brest.karas.model.Product;
import by.brest.karas.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
class ProductDaoJdbcIntegrationTest {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoJdbc.class);

    @Autowired
    private ProductDao productDao;

    @Test
    void findAllTest() {
        findAllAssertion();
    }

    private List<Product> findAllAssertion() {
        List<Product> products = productDao.findAll();
        assertNotNull(products);
        assertTrue(products.size() > 0);
        return products;
    }

    @Test
    void findProductsByDescriptionIntegrationTest() {
        List<Product> products = findAllAssertion();
        String filter = "{]@*";
        Product testProduct = new Product("test picture", filter, "test detail description", BigDecimal.valueOf(0.01),/* Date.valueOf("2001-1-1"), Date.valueOf("2001-1-1"),*/ 1);
        productDao.create(testProduct);
        products = findAllAssertion();
        products = productDao.findProductsByDescription(filter);
        assertNotNull(products);
        assertTrue(products.size() == 1);

    }

    @Test
    public void createProductWithSameShortDescriptionIntegrationTest() {
        findAllAssertion();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productDao.create(new Product("test picture", "a", "test detail description", BigDecimal.valueOf(0.01), /*Date.valueOf("2001-1-1"), Date.valueOf("2001-1-1"),*/ 1));
            productDao.create(new Product("test picture", "a", "test detail description", BigDecimal.valueOf(0.01), /*Date.valueOf("2001-1-1"), Date.valueOf("2001-1-1"),*/ 1));
        });
    }


    @Test
    void getProduct() {
    }

    @Test
    void getProducts() {
    }
}