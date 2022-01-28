package by.brest.karas.service;

import by.brest.karas.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProduct(Long id);

    List<Product> getCartProducts(Long userId);

    List<Product> getProducts(String filter);

    void save(Product product);

    void update(Long id, Product updatedProduct);

    void delete(Long id);
}
