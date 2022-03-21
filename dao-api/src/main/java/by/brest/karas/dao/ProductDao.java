package by.brest.karas.dao;

import by.brest.karas.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    Product findById(Integer id);

    List<Product> findCartProductsByUserId(Long userId);

    List<Product> findProductsByDescription(String filter);

    Integer create(Product product);

    Integer update(Product updatedProduct);

    Integer delete(Integer id);
}
