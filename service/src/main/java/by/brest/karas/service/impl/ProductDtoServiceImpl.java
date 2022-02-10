package by.brest.karas.service.impl;

import by.brest.karas.dao.ProductDtoDao;
import by.brest.karas.model.dto.ProductDto;
import by.brest.karas.service.ProductDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductDtoServiceImpl implements ProductDtoService {

    private final ProductDtoDao productDtoDao;

    public ProductDtoServiceImpl(ProductDtoDao productDtoDao) {
        this.productDtoDao = productDtoDao;
    }

//    public ProductDtoServiceImpl() {
//    }

    @Override
    public List<ProductDto> findAll() {
        return productDtoDao.findAllDto();
    }
}
