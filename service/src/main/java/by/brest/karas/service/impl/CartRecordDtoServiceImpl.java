package by.brest.karas.service.impl;

import by.brest.karas.dao.CartRecordDtoDao;
import by.brest.karas.model.dto.CartRecordDto;
import by.brest.karas.service.CartRecordDtoService;

import java.math.BigDecimal;
import java.util.List;

public class CartRecordDtoServiceImpl implements CartRecordDtoService {

    private CartRecordDtoDao cartRecordDtoDao;

    public CartRecordDtoServiceImpl(CartRecordDtoDao cartRecordDtoDao) {
        this.cartRecordDtoDao = cartRecordDtoDao;
    }

    @Override
    public List<CartRecordDto> findCartRecordDtosByCustomerId(Integer customerId, String filter) {
        return cartRecordDtoDao.findCartRecordDtosByCustomerId(customerId, filter);
    }

    @Override
    public BigDecimal findCartRecordDtosSumByCustomerId(Integer customerId, String filter) {
        return cartRecordDtoDao.findCartRecordDtosSumByCustomerId(customerId, filter);
    }
}
