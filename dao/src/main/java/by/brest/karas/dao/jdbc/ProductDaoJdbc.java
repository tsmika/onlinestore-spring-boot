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
    private String selectByDescriptionSql;// = "SELECT P.PRODUCT_ID, P.PICTURE, P.SHORT_DESCRIPTION, P.DETAIL_DESCRIPTION, P.PRICE, P.CREATION_DATE, P.UPDATE_DATE, P.CHANGED_BY FROM PRODUCT AS P WHERE P.SHORT_DESCRIPTION LIKE \'%" + FILTER + "%\' ORDER BY P.SHORT_DESCRIPTION";

    @Value("${product.create}")
    private String createSql;

    @Value("${product.checkShortDescription}")
    private String checkShortDescriptionSql;

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
    public Product findProductById(Long id) {
        return null;
    }

    @Override
    public List<Product> findCartProductsByUserId(Long userId) {
        return null;
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

        if(!isShortDescriptionUnique(product)){
            LOGGER.warn("Product with the same short description already exists in DB: {}", product);
            throw new IllegalArgumentException("Product with the same short description already exists in DB");
        }

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource
                .addValue("PICTURE", product.getPicture())
                .addValue("SHORT_DESCRIPTION", product.getShortDescription())
                .addValue("DETAIL_DESCRIPTION", product.getDetailDescription())
                .addValue("PRICE", product.getPrice())
                .addValue("CREATION_DATE", product.getCreationDate())
                .addValue("UPDATE_DATE", product.getUpdateDate())
                .addValue("CHANGED_BY", product.getChangedBy());

        return namedParameterJdbcTemplate.update(createSql, mapSqlParameterSource);
    }

    private boolean isShortDescriptionUnique(Product product){
        return namedParameterJdbcTemplate.queryForObject(checkShortDescriptionSql, new MapSqlParameterSource("SHORT_DESCRIPTION", product.getShortDescription()), Integer.class) == 0;
    }


    @Override
    public void update(Long id, Product updatedProduct) {

    }

    @Override
    public void delete(Long id) {

    }
}
