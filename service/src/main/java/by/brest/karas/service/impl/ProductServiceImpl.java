package by.brest.karas.service.impl;

import by.brest.karas.dao.ProductDao;
import by.brest.karas.model.Product;
import by.brest.karas.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductDao productDao;

    public ProductServiceImpl() {
    }

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productDao.findById(id);
    }

    @Override
    public List<Product> findCartProductsByUserId(Long userId) {
        return productDao.findCartProductsByUserId(userId);
    }

    @Override
    public List<Product> findProductsByDescription(String description) {
        return productDao.findProductsByDescription(description);
    }

    @Override
    public Integer create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Integer update(Product updatedProduct) {
        return productDao.update(updatedProduct);
    }

    @Override
    public void delete(Long id) {
        productDao.delete(id);
    }
}
