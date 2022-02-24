package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CartDao;
import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Product;
import by.brest.karas.model.dto.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CartDaoJdbc implements CartDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    private RowMapper<CartRecord> rowMapper = BeanPropertyRowMapper.newInstance(CartRecord.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(CartDaoJdbc.class);

    @Value("${cartRecord.findCartRecordsByCustomerId}")
    private String findCartRecordsByCustomerIdSql;

//    @Value("${cart.findCartSumTotalSql}")
    private String findCartSumTotalSql = "DROP VIEW IF EXISTS CART_SUM;" +
        " CREATE VIEW CART_SUM AS SELECT CART_RECORDS.PRODUCT_ID, CART_RECORDS.QUANTITY, PRODUCT.PRICE, CART_RECORDS.QUANTITY*PRODUCT.PRICE" +
        " AS SUMMA FROM CART_RECORDS, PRODUCT WHERE CART_RECORDS.PRODUCT_ID = PRODUCT.PRODUCT_ID AND CUSTOMER_ID=1; " +
        " SELECT SUM(SUMMA) FROM CART_SUM";

    public CartDaoJdbc() {
    }

    public CartDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Cart findCartByCustomerIdWithSumTotal(Integer customerId) {
        LOGGER.debug("findCartByCustomerIdWithSumTotal()");
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("ID", customerId);

        List<CartRecord> cartRecords = namedParameterJdbcTemplate.query(
                findCartRecordsByCustomerIdSql, sqlParameterSource,
                BeanPropertyRowMapper.newInstance(CartRecord.class));

        BigDecimal cartSumTotal = namedParameterJdbcTemplate.query(findCartSumTotalSql, BeanPropertyRowMapper.newInstance(BigDecimal.class)).get(0).setScale(2);
//        BigDecimal cartSumTotal = namedParameterJdbcTemplate.execute(findCartSumTotalSql).get(0).setScale(2);
//        BigDecimal cartSumTotal = jdbcTemplate.execute(findCartSumTotalSql);

        return new Cart(customerId, cartRecords, cartSumTotal);
    }
}
