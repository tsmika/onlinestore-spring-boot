package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.ProductDao;
import by.brest.karas.model.Customer;
import by.brest.karas.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

public class ProductDaoJdbc implements ProductDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoJdbc.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);

    @Value("${product.selectAll}")
    private String selectAllSql;

    @Value("${product.selectByDescription}")
    private String selectByDescriptionSql;

    @Value("${product.create}")
    private String createSql;

    @Value("${product.update}")
    private String updateSql;

    @Value("${product.delete}")
    private String deleteSql;

    @Value("${product.checkShortDescription}")
    private String checkShortDescriptionSql;

    @Value("${product.selectById}")
    private String selectByIdSql;

    public ProductDaoJdbc() {
    }

    public ProductDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        LOGGER.debug("Find all products");
        return namedParameterJdbcTemplate.query(selectAllSql, rowMapper);
    }

    @Override
    public Product findById(Integer id) {
        LOGGER.debug("Find products by id");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("ID", id);
        return namedParameterJdbcTemplate.query(selectByIdSql, sqlParameterSource, rowMapper).get(0);
    }

    @Override
    public List<Product> findProductsByDescription(String filter) {
        LOGGER.debug("Find products by description");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("FILTER", "%" + filter + "%");
        return namedParameterJdbcTemplate.query(selectByDescriptionSql, sqlParameterSource, rowMapper);
    }

    @Override
    public Integer create(Product product) {

        LOGGER.debug("Create product: {}", product);

        if (!isShortDescriptionUnique(product)) {
            LOGGER.warn("Product with the same short description already exists in DB: {}", product);
            throw new IllegalArgumentException("Product with the same short description already exists in DB");
        }

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource
                .addValue("PICTURE", product.getPicture())
                .addValue("SHORT_DESCRIPTION", product.getShortDescription())
                .addValue("DETAIL_DESCRIPTION", product.getDetailDescription())
                .addValue("PRICE", product.getPrice())
                .addValue("CHANGED_BY", product.getChangedBy());

        return namedParameterJdbcTemplate.update(createSql, mapSqlParameterSource);
    }

    public boolean isShortDescriptionUnique(Product product) {
        return namedParameterJdbcTemplate.queryForObject(checkShortDescriptionSql,
                new MapSqlParameterSource("SHORT_DESCRIPTION", product.getShortDescription()), Integer.class) == 0;
    }

    @Override
    public Integer update(Product updatedProduct) {
        LOGGER.debug("Update product: {}", updatedProduct);
        return namedParameterJdbcTemplate.update(updateSql, new MapSqlParameterSource()
                .addValue("PRODUCT_ID", updatedProduct.getProductId())
                .addValue("PICTURE", "Updated " + updatedProduct.getPicture())
                .addValue("SHORT_DESCRIPTION", updatedProduct.getShortDescription())
                .addValue("DETAIL_DESCRIPTION", updatedProduct.getDetailDescription())
                .addValue("PRICE", updatedProduct.getPrice())
                .addValue("CHANGED_BY", updatedProduct.getChangedBy()));
    }

    @Override
    public Integer delete(Integer id) {
        LOGGER.debug("Delete product: {}", id);
        return namedParameterJdbcTemplate.update(deleteSql, new MapSqlParameterSource()
                .addValue("ID", id));
    }
}
