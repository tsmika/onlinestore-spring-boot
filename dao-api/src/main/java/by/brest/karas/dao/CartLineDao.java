package by.brest.karas.dao;

import by.brest.karas.model.dto.CartLine;

import java.math.BigDecimal;
import java.util.List;

/**
 * CartRecordDto DAO interface
 */
public interface CartLineDao {

    /**
     * Get cart with total sum
     *
     * @return cart's total sum
     */

//    Cart findCartByCustomerIdWithSumTotal(Integer customerId);
    List<CartLine> findCartLinesByCustomerId(Integer customerId, String filter);

    BigDecimal findCartLinesSumByCustomerId(Integer customerId, String filter);
}
