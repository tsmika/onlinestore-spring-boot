package by.brest.karas.dao;

import by.brest.karas.model.Product;
import by.brest.karas.model.dto.Cart;

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

    Cart findCartByCustomerIdWithSumTotal(Integer customerId);
}
