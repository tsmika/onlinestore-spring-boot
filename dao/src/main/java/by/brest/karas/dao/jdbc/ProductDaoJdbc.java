package by.brest.karas.dao.jdbc;

import by.brest.karas.dao.ProductDao;
import by.brest.karas.model.Product;

import java.util.List;

public class ProductDaoJdbc implements ProductDao {
    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product getProduct(Long id) {
        return null;
    }

    @Override
    public List<Product> getCartProducts(Long userId) {
        return null;
    }

    @Override
    public List<Product> getProducts(String filter) {
        return null;
    }

    @Override
    public void save(Product product) {

    }

    @Override
    public void update(Long id, Product updatedProduct) {

    }

    @Override
    public void delete(Long id) {

    }
}
