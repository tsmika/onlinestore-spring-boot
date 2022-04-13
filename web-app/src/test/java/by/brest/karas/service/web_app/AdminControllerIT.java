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
    public void shouldForbidReturnProductInfoPage() throws Exception {
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
    public void shouldForbidReturnNewProductForm() throws Exception {
        mockMvc.perform(
                        get("/admins/1/products/new").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldAddNewProduct() throws Exception {

        mockMvc.perform(
                        post("/admins/1/products/new")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("shortDescription", "shortDescription test")
                                .param("detailDescription", "detailDescription test")
                                .param("price", "1.23")
                                .with(user("admin1").password("1").roles("ADMIN"))
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/admins/{admin_id}/products"))
                .andExpect(redirectedUrl("/admins/1/products?login=admin1&principal_role=admin"));
    }

    @Test
    public void shouldForbidAddNewProduct() throws Exception {

        mockMvc.perform(
                post("/admins/1/products/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("shortDescription", "shortDescription test")
                        .param("detailDescription", "detailDescription test")
                        .param("price", "1.23")
                        .with(user("admin1").password("1").roles("USER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(status().isForbidden());
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
    public void shouldForbidReturnEditProductPage() throws Exception {
        mockMvc.perform(
                        get("/admins/1/products/1/edit").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldUpdateProductAfterEdit() throws Exception {

        Product product = new Product();
        product.setShortDescription("Short description before");
        product.setDetailDescription("Detail description before");
        product.setPrice(BigDecimal.valueOf(1.11));

        mockMvc.perform(
                        patch("/admins/1/products/1/edit")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("shortDescription", "Short description after")
                                .param("detailDescription", "Detail description after")
                                .param("price", BigDecimal.valueOf(2.22).toString())
                                .sessionAttr("product", product)
                                .with(user("admin1").password("1").roles("ADMIN"))
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/admins/{admin_id}/products"))
                .andExpect(redirectedUrl("/admins/1/products?login=admin1&principal_role=admin"));
    }

    @Test
    public void shouldForbidUpdateProductAfterEdit() throws Exception {
        mockMvc.perform(
                patch("/admins/1/products/1/edit")
                        .with(user("admin1").password("1").roles("USER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(status().isForbidden());
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        mockMvc.perform(
                        get("/admins/1/products/1/delete").with(user("admin1").password("1").roles("ADMIN")))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admins/{admin_id}/products"))
                .andExpect(redirectedUrl("/admins/1/products?login=admin1&principal_role=admin"));
    }

    @Test
    public void shouldForbidDeleteProduct() throws Exception {
        mockMvc.perform(get("/admins/1/products/1/delete").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnCustomersPage() throws Exception {

        mockMvc.perform(
                        get("/admins/1/customers").with(user("admin1").password("1").roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("filter", "customers"))
                .andExpect(model().attribute("principal_role", "admin"))
                .andExpect(view().name("customers"))
                .andExpect(model().attribute("customers", hasItem(
                        allOf(
                                hasProperty("customerId", is(1)),
                                hasProperty("login", is("admin1")),
                                hasProperty("role", is(Role.ROLE_ADMIN)),
                                hasProperty("isActual", is(true))
                        ))))
                .andExpect(model().attribute("customers", hasItem(
                        allOf(
                                hasProperty("customerId", is(2)),
                                hasProperty("login", is("admin2")),
                                hasProperty("role", is(Role.ROLE_ADMIN)),
                                hasProperty("isActual", is(true))
                        ))))
                .andExpect(model().attribute("customers", hasItem(
                        allOf(
                                hasProperty("customerId", is(3)),
                                hasProperty("login", is("customer1")),
                                hasProperty("role", is(Role.ROLE_USER)),
                                hasProperty("isActual", is(true))
                        ))))
                .andExpect(model().attribute("customers", hasItem(
                        allOf(
                                hasProperty("customerId", is(4)),
                                hasProperty("login", is("customer2")),
                                hasProperty("role", is(Role.ROLE_USER)),
                                hasProperty("isActual", is(true))
                        ))));
    }

    @Test
    public void shouldForbidToReturnCustomersPage() throws Exception {
        mockMvc.perform(
                        get("/admins/1/customers").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnCustomerInfoPage() throws Exception {

        mockMvc.perform(
                        get("/admins/1/customers/1").with(user("admin1").password("1").roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("customer", isA(Customer.class)))
                .andExpect(model().attribute("login", "admin1"))
                .andExpect(model().attribute("admin_id", "1"))
                .andExpect(model().attribute("principal_role", "admin"))
                .andExpect(view().name("customer_info"));
    }

    @Test
    public void shouldForbidToReturnCustomerInfoPage() throws Exception {
        mockMvc.perform(
                        get("/admins/1/customers/1").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnEditCustomerPage() throws Exception {

        mockMvc.perform(
                        get("/admins/1/customers/1/edit").with(user("admin1").password("1").roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("customer", isA(Customer.class)))
                .andExpect(model().attribute("login", "admin1"))
                .andExpect(model().attribute("admin_id", "1"))
                .andExpect(model().attribute("principal_role", "admin"))
                .andExpect(view().name("edit_customer"));
    }

    @Test
    public void shouldForbidToReturnEditCustomerPage() throws Exception {
        mockMvc.perform(
                        get("/admins/1/customers/1/edit").with(user("admin1").password("1").roles("USER")))
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
                        patch("/admins/1/customers/1/edit")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("login", "Login after")
                                .param("password", "Password after")
                                .param("role", Role.ROLE_ADMIN.toString())
                                .param("isActual", Boolean.FALSE.toString())
                                .sessionAttr("customer", customer)
                                .with(user("admin1").password("1").roles("ADMIN"))
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/admins/{admin_id}/customers"))
                .andExpect(redirectedUrl("/admins/1/customers?login=admin1&principal_role=admin&customer_id=1"));
    }

    @Test
    public void shouldForbidToUpdateCustomerAfterEdit() throws Exception {
        mockMvc.perform(
                patch("/admins/1/customers/1/edit")
                        .with(user("admin1").password("1").roles("USER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(status().isForbidden());
    }

    @Test
    public void shouldDeleteCustomer() throws Exception {
        mockMvc.perform(
                        get("/admins/1/customers/1/delete").with(user("admin1").password("1").roles("ADMIN")))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admins/{admin_id}/customers"))
                .andExpect(redirectedUrl("/admins/1/customers?login=admin1&principal_role=admin"));
    }

    @Test
    public void shouldForbidToDeleteCustomer() throws Exception {
        mockMvc.perform(get("/admins/1/customers/1/delete").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnCartPage() throws Exception {

        mockMvc.perform(
                        get("/admins/1/cart/products").with(user("admin1").password("1").roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("filter", "cart_lines", "cart_sum_total"))
                .andExpect(model().attribute("principal_role", "admin"))
                .andExpect(view().name("cart"))
                .andExpect(model().attribute("cart_sum_total", BigDecimal.valueOf(18.87)))
                .andExpect(model().attribute("cart_lines", hasItem(
                        allOf(
                                hasProperty("customerId", is(1)),
                                hasProperty("productId", is(2)),
                                hasProperty("shortDescription", is("Short description for product 2")),
                                hasProperty("quantity", is(1)),
                                hasProperty("price", is(BigDecimal.valueOf(2.22))),
                                hasProperty("summa", is(BigDecimal.valueOf(2.22)))
                        ))))
                .andExpect(model().attribute("cart_lines", hasItem(
                        allOf(
                                hasProperty("customerId", is(1)),
                                hasProperty("productId", is(3)),
                                hasProperty("shortDescription", is("Short description for product 3")),
                                hasProperty("quantity", is(5)),
                                hasProperty("price", is(BigDecimal.valueOf(3.33))),
                                hasProperty("summa", is(BigDecimal.valueOf(16.65)))
                        ))));
    }

    @Test
    public void shouldForbidToReturnCartPage() throws Exception {
        mockMvc.perform(
                        get("/admins/1/cart/products").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnNewCartRecordForm() throws Exception {

        mockMvc.perform(
                        get("/admins/1/cart/products/2/new").with(user("admin1").password("1").roles("ADMIN")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("cartRecord", isA(CartRecord.class)))
                .andExpect(model().attribute("login", "admin1"))
                .andExpect(model().attribute("admin_id", "1"))
                .andExpect(model().attribute("principal_role", "admin"))
                .andExpect(model().attribute("productId", 2))
                .andExpect(model().attribute("productDescription", "Short description for product 2"))
                .andExpect(model().attribute("productPrice", BigDecimal.valueOf(2.22)))
                .andExpect(model().attribute("quantity", 1))
                .andExpect(view().name("new_cart_record_form"));
    }

    @Test
    public void shouldForbidToReturnNewCartRecordForm() throws Exception {
        mockMvc.perform(
                        get("/admins/1/cart/products/2/new").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldCreateNewCartRecord() throws Exception {

        mockMvc.perform(
                        post("/admins/1/cart/products/1")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("quantity", "2")
                                .with(user("admin1").password("1").roles("ADMIN"))
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/admins/{admin_id}/cart/products"))
                .andExpect(redirectedUrl("/admins/1/cart/products?login=admin1&principal_role=admin&productId=1&productDescription=Short+description+for+product+1&productPrice=1.11"));
    }

    @Test
    public void shouldForbidToCreateNewCartRecord() throws Exception {

        mockMvc.perform(
                post("/admins/1/cart/products/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("quantity", "2")
                        .with(user("admin1").password("1").roles("USER"))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
        ).andExpect(status().isForbidden());
    }

    @Test
    public void shouldDeleteCartRecord() throws Exception {
        mockMvc.perform(
                        get("/admins/1/cart/products/2/delete").with(user("admin1").password("1").roles("ADMIN")))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admins/{admin_id}/cart/products"))
                .andExpect(redirectedUrl("/admins/1/cart/products?login=admin1&principal_role=admin"));
    }

    @Test
    public void shouldForbidToDeleteCartRecord() throws Exception {
        mockMvc.perform(
                        get("/admins/1/cart/products/2/delete").with(user("admin1").password("1").roles("USER")))
                .andExpect(status().isForbidden());
    }
}
