package by.brest.karas.service.impl;

import by.brest.karas.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
class ProductServiceImplIntegrationTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    void findAllIntegrationTest() {
        findAllAssertion();
    }

    private List<Product> findAllAssertion() {
        List<Product> products = productService.findAll();
        assertNotNull(products);
        assertTrue(products.size() > 0);
        return products;
    }

    @Test
    void findByIdIntegrationTest() {
        List<Product> products = findAllAssertion();
        Product testProduct = productService.findById(products.get(0).getProductId());
        assertNotNull(testProduct);
        assertEquals(1, (int) testProduct.getProductId());
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            productService.findById(Integer.MAX_VALUE);
        });

    }

    @Test
    void findProductsByDescriptionIdIntegrationTest() {
        List<Product> products = findAllAssertion();
        Product testProduct = products.get(2);
        products = productService.findProductsByDescription("Short description for product 3");
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(testProduct, products.get(0));
    }

    @Test
    void createIntegrationTest() {
        List<Product> products = findAllAssertion();
        int sizeBefore = products.size();
        Product testProduct = new Product("test pic", "test short description", "test detail description", BigDecimal.valueOf(5.55), 1);
        productService.create(testProduct);
        products = productService.findAll();
        assertNotNull(products);
        assertEquals(sizeBefore, products.size() - 1);
        assertEquals("test short description", products.get(sizeBefore).getShortDescription());
        Product productWithTheSameDescription = new Product();
        productWithTheSameDescription.setShortDescription(testProduct.getShortDescription());
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            productService.create(productWithTheSameDescription);
        });
    }

    @Test
    public void updateIntegrationTest() {
        findAllAssertion();
        Product product = productService.findById(1);

        product.setPicture(product.getPicture());
        product.setShortDescription("testShortDescription");
        product.setDetailDescription("testDetailDescription");
        product.setPrice(BigDecimal.valueOf(1000000000.01));
        product.setChangedBy(3);

        productService.update(product);
        findAllAssertion();
        Product productAfter = productService.findById(1);

        assertEquals(product.getProductId(), productAfter.getProductId());
        assertEquals(("Updated " + product.getPicture()), productAfter.getPicture());
        assertEquals(product.getShortDescription(), productAfter.getShortDescription());
        assertEquals(product.getDetailDescription(), productAfter.getDetailDescription());
        assertEquals(product.getPrice(), productAfter.getPrice());
        assertEquals(product.getCreationDate(), productAfter.getCreationDate());
        assertNotEquals(product.getUpdateDate(), productAfter.getUpdateDate());
        assertEquals(product.getChangedBy(), productAfter.getChangedBy());
    }

    @Test
    public void deleteIntegrationTest() {
        List<Product> products = findAllAssertion();
        assertNotNull(products);
        int sizeBefore = products.size();
        assertEquals(5, sizeBefore);
        productService.delete(1);
        products = findAllAssertion();
        assertNotNull(products);
        int sizeAfter = products.size();
        assertEquals(sizeBefore - 1, sizeAfter);
    }
}