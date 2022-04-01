package by.brest.karas.service;

import by.brest.karas.model.CartRecord;

import java.util.List;

public interface CartRecordService {

    List<CartRecord> findCartRecordsByCustomerId(Integer customerId);

    List<CartRecord> findCartRecordsByCustomerIdAndProductId(Integer userId, Integer productId);

    Boolean isCartRecordExist(Integer customerId, Integer productId);

    Integer create(CartRecord cartRecord);

    Integer delete(Integer customerId, Integer productId);
}