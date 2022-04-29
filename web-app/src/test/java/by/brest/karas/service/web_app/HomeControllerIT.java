package by.brest.karas.service.web_app;

import by.brest.karas.model.Customer;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml", "classpath*:security-test.xml"})
@Transactional
public class HomeControllerIT {

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
    public void shouldRedirectToStartPage() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:products"))
                .andExpect(redirectedUrl("products?principal_role=guest"));
    }

    @Test
    public void shouldReturnStartPage() throws Exception {

        mockMvc.perform(
                        get("/products")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("filter", "view", "products"))
                .andExpect(model().attribute("principal_role", "guest"))
                .andExpect(view().name("products"))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is(1)),
                                hasProperty("picture", is("Pic1")),
                                hasProperty("shortDescription", is("Short description for product 1")),
                                hasProperty("detailDescription", is("Detail description for product 1")),
                                hasProperty("price", is(BigDecimal.valueOf(1.11))),
                                hasProperty("changedBy", is(1))
                        ))))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is(2)),
                                hasProperty("picture", is("Pic2")),
                                hasProperty("shortDescription", is("Short description for product 2")),
                                hasProperty("detailDescription", is("Detail description for product 2")),
                                hasProperty("price", is(BigDecimal.valueOf(2.22))),
                                hasProperty("changedBy", is(1))
                        ))))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is(3)),
                                hasProperty("picture", is("Pic3")),
                                hasProperty("shortDescription", is("Short description for product 3")),
                                hasProperty("detailDescription", is("Detail description for product 3")),
                                hasProperty("price", is(BigDecimal.valueOf(3.33))),
                                hasProperty("changedBy", is(1))
                        ))))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is(4)),
                                hasProperty("picture", is("Pic4")),
                                hasProperty("shortDescription", is("Short description for product 4")),
                                hasProperty("detailDescription", is("Detail description for product 4")),
                                hasProperty("price", is(BigDecimal.valueOf(4.44))),
                                hasProperty("changedBy", is(1))
                        ))))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is(5)),
                                hasProperty("picture", is("Pic5")),
                                hasProperty("shortDescription", is("Short description for product 5")),
                                hasProperty("detailDescription", is("Detail description for product 5")),
                                hasProperty("price", is(BigDecimal.valueOf(5.55))),
                                hasProperty("changedBy", is(1))
                        ))))
        ;
    }

    @Test
    public void shouldReturnProductInfoPage() throws Exception {

        mockMvc.perform(
                        get("/products/1")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("principal_role", "guest"))
                .andExpect(view().name("product_info"))
                .andExpect(model().attribute("product", hasProperty("productId", is(1))))
                .andExpect(model().attribute("product", hasProperty("picture", is("Pic1"))))
                .andExpect(model().attribute("product", hasProperty("shortDescription", is("Short description for product 1"))))
                .andExpect(model().attribute("product", hasProperty("detailDescription", is("Detail description for product 1"))))
                .andExpect(model().attribute("product", hasProperty("price", is(BigDecimal.valueOf(1.11)))))
                .andExpect(model().attribute("product", hasProperty("changedBy", is(1))))
        ;
    }

    @Test
    public void shouldForbidReturnProductsPageForUserOrAdmin() throws Exception {
        mockMvc.perform(get("/admins/1/products").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());

        mockMvc.perform(get("/customers/1/products").with(user("customer1").password("1").roles("ADMIN")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnProductsPageForUserOrAdmin() throws Exception {

        mockMvc.perform(get("/admins/1/products").with(user("admin1").password("1").roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("filter", "view", "products"))
                .andExpect(model().attribute("login", "admin1"))
                .andExpect(model().attribute("admin_id", 1))
                .andExpect(model().attribute("principal_role", "admin"))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is(1)),
                                hasProperty("picture", is("Pic1")),
                                hasProperty("shortDescription", is("Short description for product 1")),
                                hasProperty("detailDescription", is("Detail description for product 1")),
                                hasProperty("price", is(BigDecimal.valueOf(1.11))),
                                hasProperty("changedBy", is(1))
                        ))));

        mockMvc.perform(get("/customers/1/products").with(user("customer1").password("1").roles("USER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("filter", "view", "products"))
                .andExpect(model().attribute("login", "customer1"))
                .andExpect(model().attribute("customer_id", "3"))
                .andExpect(model().attribute("principal_role", "customer"))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is(1)),
                                hasProperty("picture", is("Pic1")),
                                hasProperty("shortDescription", is("Short description for product 1")),
                                hasProperty("detailDescription", is("Detail description for product 1")),
                                hasProperty("price", is(BigDecimal.valueOf(1.11))),
                                hasProperty("changedBy", is(1))
                        ))));
    }

    @Test
    public void shouldReturnLoginPage() throws Exception {
        mockMvc.perform(
                        get("/login")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("login"));
    }

    @Test
    public void shouldReturnNewCustomerPage() throws Exception {
        mockMvc.perform(
                        get("/new_customer")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("customer", isA(Customer.class)))
                .andExpect(model().attribute("principal_role", "guest"))
                .andExpect(view().name("new_customer_form"));
    }

    @Test
    public void shouldCreateNewCustomer() throws Exception {
        mockMvc.perform(
                        post("/new_customer")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("login", "testLogin")
                                .param("password", "testPassword")
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:products"))
                .andExpect(redirectedUrl("products?principal_role=guest"))
        ;
    }
}