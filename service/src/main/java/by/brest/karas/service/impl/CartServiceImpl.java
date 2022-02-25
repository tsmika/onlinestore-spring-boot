package by.brest.karas.service.impl;

import by.brest.karas.dao.CartDao;
import by.brest.karas.model.dto.Cart;
import by.brest.karas.service.CartService;

public class CartServiceImpl implements CartService {
    private CartDao cartDao;

    public CartServiceImpl(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public Cart findCartByCustomerId(Integer customerId) {
        return cartDao.findCartByCustomerId(customerId);
    }
}
