package by.brest.karas.service.impl;

import by.brest.karas.dao.CartLineDao;
import by.brest.karas.model.dto.CartLine;
import by.brest.karas.service.CartLineService;

import java.math.BigDecimal;
import java.util.List;

public class CartLineServiceImpl implements CartLineService {
    private CartLineDao cartLineDao;

    public CartLineServiceImpl(CartLineDao cartLineDao) {
        this.cartLineDao = cartLineDao;
    }

    @Override
    public List<CartLine> findCartLinesByCustomerId(Integer customerId, String filter) {
        return cartLineDao.findCartLinesByCustomerId(customerId, filter);
    }

    @Override
    public BigDecimal findCartLinesSumByCustomerId(Integer customerId, String filter) {
        return cartLineDao.findCartLinesSumByCustomerId(customerId, filter);
    }
}
