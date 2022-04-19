package by.brest.karas.service.web_app;

import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Customer;
import by.brest.karas.model.Product;
import by.brest.karas.model.Role;
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

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml", "classpath*:security-test.xml"})
@Transactional
public class CustomerControllerIT {
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
                        get("/customers/3/products/1").with(user("customer1").password("1").roles("USER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("product", isA(Product.class)))
                .andExpect(model().attribute("login", "customer1"))
                .andExpect(model().attribute("customer_id", "3"))
                .andExpect(model().attribute("principal_role", "customer"))
                .andExpect(view().name("product_info"));
    }

    @Test
    public void shouldForbidReturnProductInfoPage() throws Exception {
        mockMvc.perform(
                        get("/customers/3/products/1").with(user("customer1").password("1").roles("ADMIN")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnCartPage() throws Exception {

        mockMvc.perform(
                        get("/customers/3/cart/products").with(user("customer1").password("1").roles("USER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("filter", "cart_lines", "cart_sum_total"))
                .andExpect(model().attribute("principal_role", "customer"))
                .andExpect(view().name("cart"))
                .andExpect(model().attribute("cart_sum_total", BigDecimal.valueOf(91.02)))
                .andExpect(model().attribute("cart_lines", hasItem(
                        allOf(
                                hasProperty("customerId", is(3)),
                                hasProperty("productId", is(3)),
                                hasProperty("shortDescription", is("Short description for product 3")),
                                hasProperty("quantity", is(8)),
                                hasProperty("price", is(BigDecimal.valueOf(3.33))),
                                hasProperty("summa", is(BigDecimal.valueOf(26.64).setScale(2, RoundingMode.CEILING)))
                        ))))
                .andExpect(model().attribute("cart_lines", hasItem(
                        allOf(
                                hasProperty("customerId", is(3)),
                                hasProperty("productId", is(4)),
                                hasProperty("shortDescription", is("Short description for product 4")),
                                hasProperty("quantity", is(12)),
                                hasProperty("price", is(BigDecimal.valueOf(4.44))),
                                hasProperty("summa", is(BigDecimal.valueOf(53.28).setScale(2, RoundingMode.CEILING)))
                        ))))
                .andExpect(model().attribute("cart_lines", hasItem(
                        allOf(
                                hasProperty("customerId", is(3)),
                                hasProperty("productId", is(5)),
                                hasProperty("shortDescription", is("Short description for product 5")),
                                hasProperty("quantity", is(2)),
                                hasProperty("price", is(BigDecimal.valueOf(5.55))),
                                hasProperty("summa", is(BigDecimal.valueOf(11.10).setScale(2, RoundingMode.CEILING)))
                        ))));
    }

    @Test
    public void shouldForbidToReturnCartPage() throws Exception {
        mockMvc.perform(
                        get("/customers/3/cart/products").with(user("customer1").password("1").roles("ADMIN")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnNewCartRecordForm() throws Exception {

        mockMvc.perform(
                        get("/customers/3/cart/products/5/new").with(user("customer1").password("1").roles("USER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("cartRecord", isA(CartRecord.class)))
                .andExpect(model().attribute("login", "customer1"))
                .andExpect(model().attribute("customer_id", "3"))
                .andExpect(model().attribute("principal_role", "customer"))
                .andExpect(model().attribute("productId", 5))
                .andExpect(model().attribute("productDescription", "Short description for product 5"))
                .andExpect(model().attribute("productPrice", BigDecimal.valueOf(5.55)))
                .andExpect(model().attribute("quantity", 2))
                .andExpect(view().name("new_cart_record_form"));
    }

    @Test
    public void shouldForbidToReturnNewCartRecordForm() throws Exception {
        mockMvc.perform(
                        get("/customers/3/cart/products/5/new").with(user("customer1").password("1").roles("ADMIN")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldCreateNewCartRecord() throws Exception {

        mockMvc.perform(
                        post("/customers/3/cart/products/1")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("quantity", "2")
                                .with(user("customer1").password("1").roles("USER"))
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/customers/{customer_id}/cart/products"))
                .andExpect(redirectedUrl("/customers/3/cart/products?principal_role=customer&login=customer1&productId=1&productDescription=Short+description+for+product+1&productPrice=1.11"));
    }

    @Test
    public void shouldForbidToCreateNewCartRecord() throws Exception {

        mockMvc.perform(
                post("/customers/3/cart/products/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("quantity", "2")
                        .with(user("customer1").password("1").roles("ADMIN"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(status().isForbidden());
    }

    @Test
    public void shouldDeleteCartRecord() throws Exception {
        mockMvc.perform(
                        get("/customers/3/cart/products/5/delete").with(user("customer1").password("1").roles("USER")))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/customers/{customer_id}/cart/products"))
                .andExpect(redirectedUrl("/customers/3/cart/products?principal_role=customer&login=customer1"));
    }

    @Test
    public void shouldForbidToDeleteCartRecord() throws Exception {
        mockMvc.perform(
                        get("/customers/3/cart/products/5/delete").with(user("customer1").password("1").roles("ADMIN")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnEditCustomerPage() throws Exception {

        mockMvc.perform(
                        get("/customers/3/edit").with(user("customer1").password("1").roles("USER")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("customer", isA(Customer.class)))
                .andExpect(model().attribute("login", "customer1"))
                .andExpect(model().attribute("customer_id", 3))
                .andExpect(model().attribute("principal_role", "customer"))
                .andExpect(view().name("edit_customer"));
    }

    @Test
    public void shouldForbidToReturnEditCustomerPage() throws Exception {
        mockMvc.perform(
                        get("/customers/3/edit").with(user("customer1").password("1").roles("ADMIN")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldUpdateCustomerAfterEdit() throws Exception {

        Customer customer = new Customer();
        customer.setLogin("Login before");
        customer.setPassword("Password before");
        customer.setRole(Role.ROLE_USER);
        customer.setIsActual(true);

        mockMvc.perform(
                        patch("/customers/3/edit")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("login", customer.getLogin())
                                .param("password", "Password after")
                                .param("role", customer.getRole().toString())
                                .param("isActual", customer.getIsActual().toString())
                                .sessionAttr("customer", customer)
                                .with(user("customer1").password("1").roles("USER"))
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/customers/{customer_id}/products"))
                .andExpect(redirectedUrl("/customers/3/products?principal_role=customer&login=customer1"));
    }

    @Test
    public void shouldForbidToUpdateCustomerAfterEdit() throws Exception {
        mockMvc.perform(
                patch("/customers/3/edit")
                        .with(user("customer1").password("1").roles("ADMIN"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(status().isForbidden());
    }
}
