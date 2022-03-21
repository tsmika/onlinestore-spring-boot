package by.brest.karas.dao;

import by.brest.karas.model.dto.CartRecordDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * CartRecordDto DAO interface
 */
public interface CartRecordDtoDao {

    /**
     * Get cart with total sum
     *
     * @return cart's total sum
     */

//    Cart findCartByCustomerIdWithSumTotal(Integer customerId);
    List<CartRecordDto> findCartRecordDtosByCustomerId(Integer customerId, String filter);

    BigDecimal findCartRecordDtosSumByCustomerId(Integer customerId, String filter);
}
