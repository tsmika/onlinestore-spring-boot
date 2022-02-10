package by.brest.karas.service.impl;

import by.brest.karas.dao.jdbc.ProductDaoJdbc;
import by.brest.karas.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
class ProductServiceImplIntegrationTest {
    @Autowired
    private ProductDaoJdbc productDao;


    @Test
    void getAllProducts() {
        List<Product> products = productDao.findAll();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }
}