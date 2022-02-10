package by.brest.karas.dao;

import by.brest.karas.model.dto.ProductDto;

import java.util.List;

public interface ProductDtoDao {
    List<ProductDto> findAllDto();
}
