package by.brest.karas.dao;

import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Product;

import java.util.List;
import java.util.Map;

public interface CartRecordDao {

    List<CartRecord> findCartRecordsByCustomerId(Integer customerId);

    Integer create(CartRecord cartRecord);

    Boolean isCartRecordExist(Integer customerId, Integer productId);

    List<CartRecord> findCartRecordsByCustomerIdAndProductId(Integer userId, Integer productId);

    Integer delete(Integer customerId, Integer productId);
}
