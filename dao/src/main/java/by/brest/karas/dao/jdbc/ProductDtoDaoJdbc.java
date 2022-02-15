package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.ProductDtoDao;
import by.brest.karas.model.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDtoDaoJdbc implements ProductDtoDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDtoDaoJdbc.class);

    @Value("${product.selectAll}")
    private String selectSql;

    public ProductDtoDaoJdbc() {
    }

    public ProductDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<ProductDto> findAllDto() {
        LOGGER.debug("findAllDto()");
        List<ProductDto> products = namedParameterJdbcTemplate.query(selectSql, BeanPropertyRowMapper.newInstance(ProductDto.class));
        return products;
    }
}
