package by.brest.karas.service.impl;

import by.brest.karas.dao.jdbc.CartRecordDtoDaoJdbc;
import by.brest.karas.model.dto.CartRecordDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class CartRecordDtoServiceImplMockTest {
    @InjectMocks
    private CartRecordDtoDaoJdbc cartRecordDtoDaoJdbc;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    public void findCartRecordDtosByCustomerIdTest() {
        String sql = "getCartRecordDtosByCustomerId";
        ReflectionTestUtils.setField(cartRecordDtoDaoJdbc, "getCartRecordDtosByCustomerIdSql", sql);
        CartRecordDto cartRecordDto1 = new CartRecordDto();
        cartRecordDto1.setSumma(BigDecimal.valueOf(1.0));
        CartRecordDto cartRecordDto2 = new CartRecordDto();
        cartRecordDto2.setSumma(BigDecimal.valueOf(2.0));
        List<CartRecordDto> cartRecordDtoList = Arrays.asList(cartRecordDto1, cartRecordDto2);

        Mockito.when(namedParameterJdbcTemplate.query(eq(sql), ArgumentMatchers.<RowMapper<CartRecordDto>>any())).thenReturn(cartRecordDtoList);
        List<CartRecordDto> cartRecordDtos = cartRecordDtoDaoJdbc.findCartRecordDtosByCustomerId(1, "");
        assertNotNull(cartRecordDtos);
        assertFalse(cartRecordDtos.isEmpty());
        assertEquals(2, cartRecordDtos.size());
        assertSame(cartRecordDtos.get(0), cartRecordDto1);
        assertSame(cartRecordDtos.get(1), cartRecordDto2);
        assertSame(cartRecordDtos, cartRecordDtoList);

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), ArgumentMatchers.<RowMapper<CartRecordDto>>any());
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void findCartRecordDtosSumByCustomerIdTest() {
        ReflectionTestUtils.setField(cartRecordDtoDaoJdbc, "findCartRecordDtosSumByCustomerIdSql", "findCartRecordDtosSumByCustomerId");

        Mockito.when(jdbcTemplate.queryForObject(eq("findCartRecordDtosSumByCustomerId"), ArgumentMatchers.eq(BigDecimal.class))).thenReturn(BigDecimal.valueOf(1.0));
        BigDecimal result = cartRecordDtoDaoJdbc.findCartRecordDtosSumByCustomerId(1, "");
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(1.0).setScale(2, RoundingMode.CEILING), result);

        Mockito.verify(jdbcTemplate).queryForObject(eq("findCartRecordDtosSumByCustomerId"), ArgumentMatchers.eq(BigDecimal.class));
        Mockito.verifyNoMoreInteractions(jdbcTemplate);
    }
}
