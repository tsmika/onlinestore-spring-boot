package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CartRecordDao;
import by.brest.karas.model.CartRecord;
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
import java.util.Map;

public class CartRecordDaoJdbc implements CartRecordDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<CartRecord> rowMapper = BeanPropertyRowMapper.newInstance(CartRecord.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(CartRecordDaoJdbc.class);

    @Value("${cartRecord.findCartRecordsByCustomerId}")
    private String findCartRecordsByCustomerIdSql;

    @Value("${cartRecord.findFilteredCartRecordsByCustomerId}")
    private String findFilteredCartRecordsByCustomerIdSql;

    public CartRecordDaoJdbc() {
    }

    public CartRecordDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<CartRecord> findCartRecordsByCustomerId(Integer customerId) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("ID", customerId);
        return namedParameterJdbcTemplate.query(findCartRecordsByCustomerIdSql, sqlParameterSource, rowMapper);
    }

    @Override
    public List<CartRecord> findFilteredCartRecordstByCustomerId(Integer customerId, String filter) {
        LOGGER.debug("findFilteredCartRecordstByCustomerId()");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("ID", customerId);
        sqlParameterSource.addValue("FILTER", "%" + filter + "%");

        List<CartRecord> cartRecords = namedParameterJdbcTemplate.query(
                findFilteredCartRecordsByCustomerIdSql, sqlParameterSource,
                BeanPropertyRowMapper.newInstance(CartRecord.class));

        return cartRecords;
    }

    @Override
    public CartRecord findCartRecord(Integer userId, Integer productId) {
        return null;
    }

    @Override
    public Map<Product, Integer> findCartByUserId(Integer userId) {
        return null;
    }

    @Override
    public Map<Product, Integer> findCartByUserLogin(String login) {

        return null;
    }

    @Override
    public void update(CartRecord cartRecordToUpdate, Integer quantity) {
    }

    @Override
    public void save(CartRecord cartRecord) {
    }

    @Override
    public void delete(Integer userId, Integer productId) {
    }

    @Override
    public Integer findQuantityFromCart(Integer userId, Integer productId) {
        return null;
    }

    @Override
    public Map<Product, Integer> findFilteredCartByUserId(Integer userId, String filter) {
        return null;
    }
}
