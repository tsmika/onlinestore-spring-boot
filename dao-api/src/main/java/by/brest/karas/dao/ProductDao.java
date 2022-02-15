package by.brest.karas.dao;

import by.brest.karas.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    Product findProductById(Long id);

    List<Product> findCartProductsByUserId(Long userId);

    List<Product> findProductsByDescription(String filter);

    Integer create(Product product);

    void update(Long id, Product updatedProduct);

    void delete(Long id);
}
