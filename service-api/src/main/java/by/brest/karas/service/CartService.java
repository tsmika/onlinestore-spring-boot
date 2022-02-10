package by.brest.karas.service;

import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Product;

import java.util.Map;

public interface CartService {

    Long getQuantityFromCart(Long userId, Long productId);

    CartRecord getCartRecord(Long userId, Long productId);

    Map<Product, Long> getFilteredCartByUserId(Long userId, String filter);

    Map<Product, Long> getCartByUserId(Long userId);

    Map<Product, Long> getCartByUserLogin(String login);

    void update(CartRecord cartRecordToUpdate, Long quantity);

    void save(CartRecord cartRecord);

    void delete(Long userId, Long productId);
}
