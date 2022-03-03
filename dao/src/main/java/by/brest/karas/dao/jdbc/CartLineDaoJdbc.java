package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CartLineDao;
import by.brest.karas.model.CartRecord;
import by.brest.karas.model.dto.CartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class CartLineDaoJdbc implements CartLineDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private JdbcTemplate jdbcTemplate;
    private RowMapper<CartRecord> cartRecordRowMapper = BeanPropertyRowMapper.newInstance(CartRecord.class);

    private RowMapper<CartLine> cartLineRowMapper = BeanPropertyRowMapper.newInstance(CartLine.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(CartLineDaoJdbc.class);

    @Value("${cartLine.findCartLinesByCustomerId}")
    private String findCartLinesByCustomerIdSql;

    @Value("${cartLine.getCartLinesByCustomerId}")
    private String getCartLinesByCustomerIdSql;

    @Value("${cartLine.findCartLinesSumTotal}")
    private String findCartLinesSumTotalSql;

    public CartLineDaoJdbc() {
    }

    public CartLineDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CartLine> findCartLinesByCustomerId(Integer customerId, String filter) {

        LOGGER.debug("findCartLinesByCustomerId()");
        String sql = findCartLinesByCustomerIdSql + customerId;
        sql = sql + "AND P.SHORT_DESCRIPTION LIKE" + "'%" + filter + "%'";
        jdbcTemplate.execute(sql);
        return namedParameterJdbcTemplate.query(getCartLinesByCustomerIdSql, cartLineRowMapper);
    }

    @Override
    public BigDecimal findCartLinesSumByCustomerId(Integer customerId, String filter) {
        return jdbcTemplate.queryForObject(findCartLinesSumTotalSql, BigDecimal.class).setScale(2);
    }
}
