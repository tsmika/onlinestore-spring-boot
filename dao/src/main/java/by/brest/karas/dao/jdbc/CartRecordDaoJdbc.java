package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CartRecordDao;
import by.brest.karas.model.CartRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

public class CartRecordDaoJdbc implements CartRecordDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<CartRecord> rowMapper = BeanPropertyRowMapper.newInstance(CartRecord.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(CartRecordDaoJdbc.class);

    @Value("${cartRecord.findCartRecordByCustomerIdAndProductId}")
    private String findCartRecordByCustomerIdAndProductIdSql;

    @Value("${cartRecord.findCartRecordsByCustomerId}")
    private String findCartRecordsByCustomerIdSql;

    @Value("${cartRecord.findFilteredCartRecordsByCustomerId}")
    private String findFilteredCartRecordsByCustomerIdSql;

    @Value("${cartRecord.findProductInCartRecordsByCustomerId}")
    private String findProductInCartRecordsByCustomerIdSql;

    @Value("${cartRecord.create}")
    private String createSql;

    @Value("${cartRecord.update}")
    private String updateSql;

    @Value("${cartRecord.delete}")
    private String deleteSql;

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
    public List<CartRecord> findCartRecordsByCustomerIdAndProductId(Integer customerId, Integer productId) {
        LOGGER.debug("findCartRecordsByCustomerIdAndProductId()");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("CUSTOMER_ID", customerId);
        sqlParameterSource.addValue("PRODUCT_ID", productId);

        List<CartRecord> cartRecords = namedParameterJdbcTemplate.query(
                findCartRecordByCustomerIdAndProductIdSql, sqlParameterSource,
                BeanPropertyRowMapper.newInstance(CartRecord.class));

        return cartRecords;
    }


    @Override
    public Boolean isCartRecordExist(Integer customerId, Integer productId) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource
                .addValue("CUSTOMER_ID", customerId)
                .addValue("PRODUCT_ID", productId);

        List<CartRecord> cartRecords = namedParameterJdbcTemplate.query(findProductInCartRecordsByCustomerIdSql, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(CartRecord.class));

//        if (cartRecords.size() > 1){
//            try {
//                throw new Exception("Duplicate cart records for user " + login);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        return cartRecords.size() == 0 ? false : true;
    }

    @Override
    public Integer create(CartRecord cartRecord) {

        String sql;

        if (isCartRecordExist(cartRecord.getCustomerId(), cartRecord.getProductId())) {
            LOGGER.debug("Update cart record: {}", cartRecord);
            sql = updateSql;
        } else {
            LOGGER.debug("Create cart record: {}", cartRecord);
            sql = createSql;
        }

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        mapSqlParameterSource
                .addValue("CUSTOMER_ID", cartRecord.getCustomerId())
                .addValue("PRODUCT_ID", cartRecord.getProductId())
                .addValue("QUANTITY", cartRecord.getQuantity());

        return namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

    @Override
    public Integer delete(Integer customerId, Integer productId) {
        LOGGER.debug("Delete cart record by customer id and product id ({},{}) ", customerId, productId);

        return namedParameterJdbcTemplate.update(deleteSql, new MapSqlParameterSource()
                .addValue("CUSTOMER_ID", customerId)
                .addValue("PRODUCT_ID", productId));
    }
}
