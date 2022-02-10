package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.ProductDao;
import by.brest.karas.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoJdbc.class);
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);

    @Value("${product.select}")
    private String selectSql;

    public ProductDaoJdbc() {
    }

    public ProductDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        LOGGER.debug("Find all products");
        return namedParameterJdbcTemplate.query(selectSql, rowMapper);
    }

    @Override
    public Product findProductById(Long id) {
        return null;
    }

    @Override
    public List<Product> findCartProductsByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Product> findProductsByDescription(String filter) {
        return null;
    }

    @Override
    public void save(Product product) {

    }

    @Override
    public void update(Long id, Product updatedProduct) {

    }

    @Override
    public void delete(Long id) {

    }
}
