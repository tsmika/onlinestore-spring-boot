package by.brest.karas.service.impl;

//import by.brest.karas.dao.CartDao;
import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Product;
import by.brest.karas.service.CartService;

import java.util.Map;

//public class CartServiceImpl implements CartService {
//
//    private CartDao cartDao;
//
//    @Override
//    public Long getQuantityFromCart(Long userId, Long productId) {
//        return cartDao.getQuantityFromCart(userId, productId);
//    }
//
//    @Override
//    public CartRecord getCartRecord(Long userId, Long productId) {
//        return cartDao.getCartRecord(userId, productId);
//    }
//
//    @Override
//    public Map<Product, Long> getFilteredCartByUserId(Long userId, String filter) {
//        return cartDao.getFilteredCartByUserId(userId, filter);
//    }
//
//    @Override
//    public Map<Product, Long> getCartByUserId(Long userId) {
//        return cartDao.getCartByUserId(userId);
//    }
//
//    @Override
//    public Map<Product, Long> getCartByUserLogin(String login) {
//        return cartDao.getCartByUserLogin(login);
//    }
//
//    @Override
//    public void update(CartRecord cartRecordToUpdate, Long quantity) {
//        cartDao.update(cartRecordToUpdate, quantity);
//    }
//
//    @Override
//    public void save(CartRecord cartRecord) {
//        cartDao.save(cartRecord);
//    }
//
//    @Override
//    public void delete(Long userId, Long productId) {
//        cartDao.delete(userId, productId);
//    }
//}
