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
    public Integer create(CartRecord cartRecord) {
        return cartRecordDao.create(cartRecord);
    }

    @Override
    public Boolean isCartRecordExist(Integer customerId, Integer productId) {
        return cartRecordDao.isCartRecordExist(customerId, productId);
    }

    @Override
    public List<CartRecord> findCartRecordsByCustomerIdAndProductId(Integer userId, Integer productId) {
        return cartRecordDao.findCartRecordsByCustomerIdAndProductId(userId, productId);
    }

    @Override
    public Integer delete(Integer customerId, Integer productId) {
        return cartRecordDao.delete(customerId, productId);
    }
}
