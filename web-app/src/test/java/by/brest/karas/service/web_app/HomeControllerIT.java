package by.brest.karas.service.web_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
public class HomeControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldReturnProductPage() throws Exception {

//        Product testProduct1 = new Product(
//                "Pic1"
//                ,"Short description for product 1"
//                ,"Detail description for product 1"
//                , BigDecimal.valueOf(1.11)
//                , new Date(Calendar.getInstance().getTime().getTime())
//                , new Date(Calendar.getInstance().getTime().getTime())
//                , 1);
//
//        testProduct1.setProductId(1);
//
//        Product testProduct2 = new Product(
//                "Pic2"
//                ,"Short description for product 2"
//                ,"Detail description for product 2"
//                , BigDecimal.valueOf(2.22)
//                , new Date(Calendar.getInstance().getTime().getTime())
//                , new Date(Calendar.getInstance().getTime().getTime())
//                , 1);
//
//        testProduct2.setProductId(2);
//
//        Product testProduct3 = new Product(
//                "Pic3"
//                ,"Short description for product 3"
//                ,"Detail description for product 3"
//                , BigDecimal.valueOf(3.33)
//                , new Date(Calendar.getInstance().getTime().getTime())
//                , new Date(Calendar.getInstance().getTime().getTime())
//                , 1);
//
//        testProduct3.setProductId(3);
//
//        List<Product> products = Arrays.asList(testProduct1, testProduct2, testProduct3);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/products")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("products"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("filter", "view", "products"))
//                .andExpect(MockMvcResultMatchers.model().attribute("products", products)) //TODO figure out why it fails

//                .andExpect(view().name("/"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("filter")
//                        .andExpect(MockMvcResultMatchers.model().attribute()
//                        )
//                .andExpect(model().attribute("p", hasItem(
//                        allOf(
//                                hasProperty("productId", is(1)),
//                                hasProperty("shortDescription", is("shortDescription1")),
//                                hasProperty("avgSalary", is(BigDecimal.valueOf(150)))
//                        )
//                )))
//                .andExpect(model().attribute("departments", hasItem(
//                        allOf(
//                                hasProperty("departmentId", is(2)),
//                                hasProperty("departmentName", is("SECURITY")),
//                                hasProperty("avgSalary", is(BigDecimal.valueOf(400)))
//                        )
//                )))
//                .andExpect(model().attribute("departments", hasItem(
//                        allOf(
//                                hasProperty("departmentId", is(3)),
//                                hasProperty("departmentName", is("MANAGEMENT")),
//                                hasProperty("avgSalary", isEmptyOrNullString())
//                        )
//                )))
        ;
    }
}