package by.brest.karas.service.impl;

import by.brest.karas.model.dto.ProductDto;
import by.brest.karas.service.ProductDtoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
public class ProductDtoServiceImplIntegrationTest {

    @Autowired
    ProductDtoService productDtoService;

    @Test
    public void shouldFindAll(){
        List<ProductDto> products = productDtoService.findAll();
        assertNotNull(products);
        assertTrue(products.size() > 0);
//        assertTrue(products.get(0)..size() > 0);

    }
}
