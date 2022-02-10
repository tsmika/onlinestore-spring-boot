package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.CartDao;
import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Customer;
import by.brest.karas.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

public class CartDaoJdbc implements CartDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Customer.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDaoJdbc.class);

    @Override
    public CartRecord getCartRecord(Long userId, Long productId) {
        return null;
    }

    @Override
    public Map<Product, Long> getCartByUserId(Long userId) {
        return null;
    }

    @Override
    public Map<Product, Long> getCartByUserLogin(String login) {
        return null;
    }

    @Override
    public void update(CartRecord cartRecordToUpdate, Long quantity) {

    }

    @Override
    public void save(CartRecord cartRecord) {

    }

    @Override
    public void delete(Long userId, Long productId) {

    }

    @Override
    public Long getQuantityFromCart(Long userId, Long productId) {
        return null;
    }

    @Override
    public Map<Product, Long> getFilteredCartByUserId(Long userId, String filter) {
        return null;
    }
}
