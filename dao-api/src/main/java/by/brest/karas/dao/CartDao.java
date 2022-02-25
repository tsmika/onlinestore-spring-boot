package by.brest.karas.dao;

import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Product;
import by.brest.karas.model.dto.Cart;

import java.util.List;
import java.util.Map;

/**
 * CartRecordDto DAO interface
 */
public interface CartDao {

    /**
     * Get cart with total sum
     *
     * @return cart's total sum
     */

//    Cart findCartByCustomerIdWithSumTotal(Integer customerId);
    Cart findCartByCustomerId(Integer customerId);
}
