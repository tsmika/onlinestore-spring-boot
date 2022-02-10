package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.ProductDao;
import by.brest.karas.model.Product;
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
    void findAll() {
        List<Product> products = productDao.findAll();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

    @Test
    void getProduct() {
    }

    @Test
    void getProducts() {
    }
}