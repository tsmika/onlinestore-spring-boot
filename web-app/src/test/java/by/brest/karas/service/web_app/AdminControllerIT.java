package by.brest.karas.service.web_app;

import by.brest.karas.model.Customer;
import by.brest.karas.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.isA;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml", "classpath*:security-test.xml"})
@Transactional
public class AdminControllerIT {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }
    @Test
    public void shouldReturnProductInfoPage() throws Exception {

        mockMvc.perform(
                        get("/admins/1/products/1").with(user("admin1").password("1").roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("product", isA(Product.class)))
                .andExpect(model().attribute("login", "admin1"))
                .andExpect(model().attribute("admin_id", "1"))
                .andExpect(model().attribute("principal_role", "admin"))
                .andExpect(view().name("product_info"));
    }

    @Test
    public void shouldNotReturnProductInfoPage() throws Exception {
        mockMvc.perform(
                        get("/admins/1/products/1").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnNewProductForm() throws Exception {

        mockMvc.perform(
                        get("/admins/1/products/new").with(user("admin1").password("1").roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("product", isA(Product.class)))
                .andExpect(model().attribute("login", "admin1"))
                .andExpect(model().attribute("admin_id", "1"))
                .andExpect(model().attribute("principal_role", "admin"))
                .andExpect(view().name("new_product_form"));
    }

    @Test
    public void shouldNotReturnNewProductForm() throws Exception {
        mockMvc.perform(
                        get("/admins/1/products/new").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnEditProductPage() throws Exception {

        mockMvc.perform(
                        get("/admins/1/products/1/edit").with(user("admin1").password("1").roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("product", isA(Product.class)))
                .andExpect(model().attribute("login", "admin1"))
                .andExpect(model().attribute("admin_id", "1"))
                .andExpect(model().attribute("principal_role", "admin"))
                .andExpect(view().name("edit_product"));
    }

    @Test
    public void shouldNotReturnEditProductPage() throws Exception {
        mockMvc.perform(
                        get("/admins/1/products/1/edit").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());
    }

//    @Test
//    public void shouldEditProduct() throws Exception {
//        mockMvc.perform(
////                        post("/admins/1/products/1/edit")
////                        patch("/admins/{admin_id}/products/{product_id}/edit", 1, 1)
//                        patch("/admins/1/products/1/edit")
////                        post("/new_customer")
////                                .with(user("admin1").password("1").roles("ADMIN"))
//                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                                .param("login", "admin1")
//                                .param("password", "1")
//                                .with(SecurityMockMvcRequestPostProcessors.csrf())
////                                .with(SecurityMockMvcRequestPostProcessors.user("admin1").password("1").roles("ADMIN"))
////                                .with(SecurityMockMvcRequestPostProcessors.user("admin1"))
////                                .with(user("admin1").password("1").roles("USER"))
//                )
//                .andExpect(status().isFound())
//                .andExpect(view().name("redirect:/admins/1/products"))
//                .andExpect(redirectedUrl("products?principal_role=guest"))
////                .andExpect(view().name("products"))
////                .andExpect(redirectedUrl("http://localhost/login"))
////                .andExpect(redirectedUrl("products?principal_role=guest"))
////
////                .andExpect(view().name("redirect:products"))
////                .andExpect(view().name("redirect:admins/1/products/1/edit"))
//        ;
//    }
}
