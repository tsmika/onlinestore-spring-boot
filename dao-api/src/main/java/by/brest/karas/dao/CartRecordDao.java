package by.brest.karas.dao;

import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Product;

import java.util.List;
import java.util.Map;

public interface CartRecordDao {

    List<CartRecord> findCartRecordsByCustomerId(Integer customerId);

    List<CartRecord> findFilteredCartRecordstByCustomerId(Integer customerId, String filter);

    Integer create(CartRecord cartRecord);

    Boolean isCartRecordExist(Integer customerId, Integer productId);

    List<CartRecord> findCartRecordsByCustomerIdAndProductId(Integer userId, Integer productId);

    ///////////////////////////////////

    Integer findQuantityFromCart(Integer userId, Integer productId);

    Map<Product, Integer> findFilteredCartByUserId(Integer userId, String filter);

    Map<Product, Integer> findCartByUserId(Integer userId);

    Map<Product, Integer> findCartByUserLogin(String login);

    void update(CartRecord cartRecordToUpdate, Integer quantity);

    void delete(Integer userId, Integer productId);
}
