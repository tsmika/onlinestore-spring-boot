package by.brest.karas.service.impl;

import by.brest.karas.model.Customer;
import by.brest.karas.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
class ProductServiceImplIntegrationTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    void getAllProducts() {
        findAllAssertion();
    }

    @Test
    void findByIdIntegrationTest() {
        List<Product> products = findAllAssertion();
        Product testProduct = productService.findById(products.get(0).getProductId());
        assertNotNull(testProduct);
        assertTrue(testProduct.getProductId() == 1);
    }

    private List<Product> findAllAssertion() {
        List<Product> products = productService.findAll();
        assertNotNull(products);
        assertTrue(products.size() > 0);
        return products;
    }

    @Test
    void createIntegrationTest() {
        List<Product> products = findAllAssertion();
        int sizeBefore = products.size();
        Product testProduct = new Product("test pic", "test short description", "test detail description", BigDecimal.valueOf(5.55), 1);
        productService.create(testProduct);
        products = productService.findAll();
        assertNotNull(products);
        assertTrue(sizeBefore == products.size() - 1);
        assertTrue(products.get(sizeBefore).getShortDescription().equals("test short description"));
    }

    @Test
    public void updateIntegrationTest() {
        List<Product> products = findAllAssertion();
        Product product = productService.findById(1);

        product.setPicture(product.getPicture());
        product.setShortDescription("testShortDescription");
        product.setDetailDescription("testDetailDescription");
        product.setPrice(BigDecimal.valueOf(1000000000.01));
        product.setChangedBy(3);

        productService.update(product);
        products = findAllAssertion();
        Product productAfter = productService.findById(1);

        assertTrue(product.getProductId().equals(productAfter.getProductId()));
        assertTrue(("Updated " + product.getPicture()).equals(productAfter.getPicture()));
        assertTrue(product.getShortDescription().equals(productAfter.getShortDescription()));
        assertTrue(product.getDetailDescription().equals(productAfter.getDetailDescription()));
        assertTrue(product.getPrice().equals(productAfter.getPrice()));
        assertTrue(product.getCreationDate().equals(productAfter.getCreationDate()));
        assertTrue(!product.getUpdateDate().equals(productAfter.getUpdateDate()));
        assertTrue(product.getChangedBy().equals(productAfter.getChangedBy()));
    }

    @Test
    public void deleteIntegrationTest() {
        List<Product> products = findAllAssertion();
        assertNotNull(products);
        int sizeBefore = products.size();
        assertTrue(sizeBefore == 5);
        productService.delete(1);
        products = findAllAssertion();
        assertNotNull(products);
        int sizeAfter = products.size();
        assertTrue(sizeBefore - 1 == sizeAfter);
    }
}