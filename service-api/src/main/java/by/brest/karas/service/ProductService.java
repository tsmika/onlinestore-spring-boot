package by.brest.karas.service;

import by.brest.karas.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findById(Integer id);

    List<Product> findCartProductsByUserId(Long userId);

    List<Product> findProductsByDescription(String description);

    Integer create(Product product);

    void update(Long id, Product updatedProduct);

    void delete(Long id);
}
