package by.brest.karas.service;

import by.brest.karas.model.dto.CartRecordDto;

import java.math.BigDecimal;
import java.util.List;

public interface CartRecordDtoService {
    List<CartRecordDto> findCartRecordDtosByCustomerId(Integer customerId, String filter);
    BigDecimal findCartRecordDtosSumByCustomerId(Integer customerId, String filter);
}
