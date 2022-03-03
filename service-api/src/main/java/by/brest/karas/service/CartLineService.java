package by.brest.karas.service;

import by.brest.karas.model.dto.CartLine;

import java.math.BigDecimal;
import java.util.List;

public interface CartLineService {
    List<CartLine> findCartLinesByCustomerId(Integer customerId, String filter);
    BigDecimal findCartLinesSumByCustomerId(Integer customerId, String filter);
}
