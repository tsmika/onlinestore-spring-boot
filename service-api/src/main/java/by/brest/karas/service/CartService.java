package by.brest.karas.service;

import by.brest.karas.model.dto.Cart;

public interface CartService {
    Cart findCartByCustomerId(Integer customerId);
}
