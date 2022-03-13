package by.brest.karas.service.impl;

import by.brest.karas.dao.CartRecordDao;
import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Product;
import by.brest.karas.service.CartRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CartRecordServiceImpl implements CartRecordService {

    private CartRecordDao cartRecordDao;

    public CartRecordServiceImpl(CartRecordDao cartRecordDao) {
        this.cartRecordDao = cartRecordDao;
    }

    @Override
    public List<CartRecord> findCartRecordsByCustomerId(Integer customerId) {
        return cartRecordDao.findCartRecordsByCustomerId(customerId);
    }

    @Override
    public List<CartRecord> findFilteredCartRecordsByCustomerId(Integer customerId, String filter) {
        return cartRecordDao.findFilteredCartRecordstByCustomerId(customerId, filter);
    }

    @Override
    public Integer create(CartRecord cartRecord) {
       return cartRecordDao.create(cartRecord);
    }

    @Override
    public Boolean isCartRecordExist(Integer customerId, Integer productId) {
        return cartRecordDao.isCartRecordExist(customerId, productId);
    }

    //////////////////////////////////////////

    @Override
    public Integer findQuantityFromCart(Integer userId, Integer productId) {
        return cartRecordDao.findQuantityFromCart(userId, productId);
    }

    @Override
    public List<CartRecord> findCartRecordsByCustomerIdAndProductId(Integer userId, Integer productId) {
        return cartRecordDao.findCartRecordsByCustomerIdAndProductId(userId, productId);
    }

    @Override
    public Map<Product, Integer> findFilteredCartByUserId(Integer userId, String filter) {
        return cartRecordDao.findFilteredCartByUserId(userId, filter);
    }

    @Override
    public Map<Product, Integer> findCartByUserId(Integer userId) {
        return null;
    }


    @Override
    public Map<Product, Integer> findCartByUserLogin(String login) {
        return cartRecordDao.findCartByUserLogin(login);
    }

    @Override
    public void update(CartRecord cartRecordToUpdate, Integer quantity) {
        cartRecordDao.update(cartRecordToUpdate, quantity);
    }

    @Override
    public void delete(Integer userId, Integer productId) {
        cartRecordDao.delete(userId, productId);
    }
}
