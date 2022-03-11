package by.brest.karas.service;

import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Product;

import java.util.List;
import java.util.Map;

public interface CartRecordService {

    List<CartRecord> findCartRecordsByCustomerId(Integer customerId);

    List<CartRecord> findFilteredCartRecordsByCustomerId(Integer customerId, String filter);

    Integer create(CartRecord cartRecord);

    //////////////////////////////////////

    Integer findQuantityFromCart(Integer userId, Integer productId);

    CartRecord findCartRecord(Integer userId, Integer productId);

    Map<Product, Integer> findFilteredCartByUserId(Integer userId, String filter);

    Map<Product, Integer> findCartByUserId(Integer userId);

    Map<Product, Integer> findCartByUserLogin(String login);



    void update(CartRecord cartRecordToUpdate, Integer quantity);

    void delete(Integer userId, Integer productId);
}
