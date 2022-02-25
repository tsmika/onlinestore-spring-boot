package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CartDao;
import by.brest.karas.model.CartRecord;
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

public class CartDaoJdbc implements CartDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private JdbcTemplate jdbcTemplate;

    private RowMapper<CartRecord> rowMapper = BeanPropertyRowMapper.newInstance(CartRecord.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(CartDaoJdbc.class);

    @Value("${cartRecord.findCartRecordsByCustomerId}")
    private String findCartRecordsByCustomerIdSql;

    @Value("${cart.findCartSumTotalSql}")
    private String findCartSumTotalSql;

    @Value("${cart.getCartSumTotalSql}")
    private String getCartSumTotalSql;

    public CartDaoJdbc() {
    }

    public CartDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cart findCartByCustomerId(Integer customerId) {
        LOGGER.debug("findCartByCustomerId()");
        jdbcTemplate.execute(findCartSumTotalSql + customerId);
        BigDecimal cartSumTotal = jdbcTemplate.queryForObject(getCartSumTotalSql, BigDecimal.class);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("ID", customerId);

        List<CartRecord> cartRecords = namedParameterJdbcTemplate.query(
                findCartRecordsByCustomerIdSql, sqlParameterSource,
                BeanPropertyRowMapper.newInstance(CartRecord.class));

//        BigDecimal cartSumTotal = findCartSumTotal(customerId);

        return new Cart(customerId, cartRecords, cartSumTotal);
    }

//    public BigDecimal findCartSumTotal(Integer customerId) {
//        return cartSumTotal.setScale(2);
//    }
}
