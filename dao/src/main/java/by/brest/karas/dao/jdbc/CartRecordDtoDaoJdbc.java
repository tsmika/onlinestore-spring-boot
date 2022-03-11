package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CartRecordDtoDao;
import by.brest.karas.model.dto.CartRecordDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

public class CartRecordDtoDaoJdbc implements CartRecordDtoDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private JdbcTemplate jdbcTemplate;

    private RowMapper<CartRecordDto> cartRecordDtoRowMapper = BeanPropertyRowMapper.newInstance(CartRecordDto.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(CartRecordDtoDaoJdbc.class);

    @Value("${cartRecordDto.findCartRecordDtosByCustomerId}")
    private String findCartRecordDtosByCustomerIdSql;

    @Value("${cartRecordDto.findCartRecordDtosSumByCustomerId}")
    private String findCartRecordDtosSumByCustomerIdSql;

    @Value("${cartRecordDto.getCartRecordDtosByCustomerId}")
    private String getCartRecordDtosByCustomerIdSql;

    public CartRecordDtoDaoJdbc() {
    }

    public CartRecordDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CartRecordDto> findCartRecordDtosByCustomerId(Integer customerId, String filter) {

        LOGGER.debug("findCartRecordDtosByCustomerId()");
        String sql = findCartRecordDtosByCustomerIdSql + customerId;
        sql = sql + "AND P.SHORT_DESCRIPTION LIKE" + "'%" + filter + "%'";
        jdbcTemplate.execute(sql);
        return namedParameterJdbcTemplate.query(getCartRecordDtosByCustomerIdSql, cartRecordDtoRowMapper);
    }

    @Override
    public BigDecimal findCartRecordDtosSumByCustomerId(Integer customerId, String filter) {
        return jdbcTemplate.queryForObject(findCartRecordDtosSumByCustomerIdSql, BigDecimal.class).setScale(2);
    }
}
