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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml", "classpath*:dao.xml"})
@Transactional
public class CartRecordDtoServiceImplIntegrationTest {

    @Autowired
    private CartRecordDtoServiceImpl cartRecordDtoService;

    @Test
    public void findCartRecordDtosByCustomerIdIntegrationTest() {
        List<CartRecordDto> cartRecordDtos = cartRecordDtoService.findCartRecordDtosByCustomerId(1, "");
        assertNotNull(cartRecordDtos);
        assertEquals(2, cartRecordDtos.size());
        assertEquals(1, (int) cartRecordDtos.get(0).getCustomerId());
    }

    @Test
    public void findCartRecordDtosSumByCustomerIdIntegrationTest() {
        List<CartRecordDto> cartRecordDtos = cartRecordDtoService.findCartRecordDtosByCustomerId(1, "");
        assertNotNull(cartRecordDtos);
        assertEquals(2, cartRecordDtos.size());
        assertEquals(1, (int) cartRecordDtos.get(0).getCustomerId());
        assertEquals(cartRecordDtoService.findCartRecordDtosSumByCustomerId(1, ""), BigDecimal.valueOf(18.87).setScale(2));
        cartRecordDtos = cartRecordDtoService.findCartRecordDtosByCustomerId(1, "{]&*%");
        assertNotNull(cartRecordDtos);
        assertEquals(0, cartRecordDtos.size());

        cartRecordDtos = cartRecordDtoService.findCartRecordDtosByCustomerId(1, "3");
        assertNotNull(cartRecordDtos);
        assertEquals(Integer.valueOf(1), cartRecordDtos.size());
        assertEquals(Integer.valueOf(1), cartRecordDtos.get(0).getCustomerId());
        assertEquals(cartRecordDtoService.findCartRecordDtosSumByCustomerId(1, "3"), BigDecimal.valueOf(16.65).setScale(2));
    }
}
