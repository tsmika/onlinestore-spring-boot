package by.brest.karas.service.impl;

import by.brest.karas.model.dto.CartRecordDto;
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
public class CartRecordDtoServiceImplIntegrationTest {

    @Autowired
    private CartRecordDtoServiceImpl cartRecordDtoService;

    @Test
    public void findCartLinesByCustomerIdIntegrationTest() {
        List<CartRecordDto> cartRecordDtos = cartRecordDtoService.findCartRecordDtosByCustomerId(1, "");
        assertNotNull(cartRecordDtos);
        assertNotNull(cartRecordDtos.size() == 2);
        assertTrue(cartRecordDtos.get(0).getCustomerId() == 1);
        assertTrue(cartRecordDtoService.findCartRecordDtosSumByCustomerId(1, "").equals(BigDecimal.valueOf(18.87).setScale(2)));

        cartRecordDtos = cartRecordDtoService.findCartRecordDtosByCustomerId(1, "aaa");
        assertNotNull(cartRecordDtos);
//        assertNotNull(cartRecordDtos.size() == 0);

        cartRecordDtos = cartRecordDtoService.findCartRecordDtosByCustomerId(1, "3");
        assertNotNull(cartRecordDtos);
        assertNotNull(cartRecordDtos.size() == 1);
        assertTrue(cartRecordDtos.get(0).getCustomerId() == 1);
        assertTrue(cartRecordDtoService.findCartRecordDtosSumByCustomerId(1, "3").equals(BigDecimal.valueOf(16.65).setScale(2)));
        System.out.println();
    }
}
