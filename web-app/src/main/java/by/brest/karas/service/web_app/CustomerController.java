package by.brest.karas.service.web_app;

import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Customer;
import by.brest.karas.model.Product;
import by.brest.karas.model.Role;
import by.brest.karas.model.dto.CartRecordDto;
import by.brest.karas.service.CartRecordService;
import by.brest.karas.service.CartRecordDtoService;
import by.brest.karas.service.CustomerService;
import by.brest.karas.service.ProductService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Customer controller.
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final ProductService productService;

    private final CustomerService customerService;

    private final CartRecordService cartRecordService;

    private final CartRecordDtoService cartLineService;

    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerController(ProductService productService, CustomerService customerService, CartRecordService cartRecordService, CartRecordDtoService cartService, CartRecordDtoService cartLineService, BCryptPasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.customerService = customerService;
        this.cartRecordService = cartRecordService;
        this.cartLineService = cartLineService;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("customer_id")
    public String getCustomerId(Principal principal) {
        return getPrincipalId(principal).toString();
    }

    @ModelAttribute("principal_role")
    public String getPrincipalRole() {
        return "customer";
    }

    private Integer getPrincipalId(Principal principal) {
        return customerService.findByLogin(principal.getName()).get().getCustomerId();
    }

    @ModelAttribute("login")
    public String getCustomerLogin(Principal principal) {
        return customerService.findById(getPrincipalId(principal)).get().getLogin();
    }

    @GetMapping("/{customer_id}/products")
    public String goToStartPage(
            @PathVariable("customer_id") Integer customerId,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            @RequestParam(value = "view", required = false, defaultValue = "") String view,
            Model model) {

        model.addAttribute("filter", filter);
        model.addAttribute("view", view);

        if (filter == null) {
            model.addAttribute("products", productService.findAll());
        } else model.addAttribute("products", productService.findProductsByDescription(filter));

        return "products";
    }

    @GetMapping(value = "/{customer_id}/products/{product_id}")
    public String goToProductPage(
            @PathVariable(value = "product_id") Integer productId,
            Model model) {

        model.addAttribute("product", productService.findById(productId));

        return "product_info";
    }

    /////////////////////// CART
    @GetMapping("/{customer_id}/cart/products")
    public String goToCartPage(
            @PathVariable("customer_id") Integer customerId,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            Model model) {

        List<CartRecordDto> cartRecordDtos = cartLineService.findCartRecordDtosByCustomerId(customerId, filter);
        model.addAttribute("filter", filter);
        model.addAttribute("cart_lines", cartRecordDtos);
        model.addAttribute("cart_sum_total", (cartRecordDtos.size() != 0) ? cartLineService.findCartRecordDtosSumByCustomerId(customerId, filter) : "0.00");

        return "cart";
    }

    @GetMapping("/{customer_id}/cart/products/{product_id}/edit")
    public String goToNewCartRecordForm() {
        return "redirect:/customers/{customer_id}/cart/products/{product_id}/new";
    }

    @GetMapping("/{customer_id}/cart/products/{product_id}/new")
    public String goToNewCartRecordForm(
            @ModelAttribute("cartRecord") CartRecord cartRecord,
            @PathVariable("customer_id") Integer customerId,
            @PathVariable("product_id") Integer productId,
            Model model) {

        Product product = productService.findById(productId);
        model.addAttribute("productId", productId);
        model.addAttribute("productDescription", product.getShortDescription());
        model.addAttribute("productPrice", product.getPrice());
        model.addAttribute("cartRecord", cartRecord);

        if (cartRecordService.isCartRecordExist(customerId, productId)) {
            model.addAttribute("quantity", cartRecordService.findCartRecordsByCustomerIdAndProductId(customerId, productId).get(0).getQuantity());
        }

        return "new_cart_record_form";
    }

    @PostMapping("/{customer_id}/cart/products/{product_id}")
    public String createCartRecord(
            @ModelAttribute("cartRecord") @Valid CartRecord cartRecord
            , BindingResult bindingResult
            , @PathVariable("product_id") Integer productId
            , @PathVariable("customer_id") Integer customerId
            , Model model) {

        Product product = productService.findById(productId);
        model.addAttribute("productId", productId);
        model.addAttribute("productDescription", product.getShortDescription());
        model.addAttribute("productPrice", product.getPrice());
        model.addAttribute("cartRecord", cartRecord);

        if (bindingResult.hasErrors()) {
            return "new_cart_record_form";
        }

        cartRecord.setCustomerId(customerId);
        cartRecord.setProductId(productId);
        cartRecordService.create(cartRecord);

        return "redirect:/customers/{customer_id}/cart/products";
    }

    @GetMapping("/{customer_id}/cart/products/{product_id}/delete")
    public String deleteCartRecordByCustomerIdAndProductId(
            @PathVariable("product_id") Integer productId,
            @PathVariable("customer_id") Integer customerId) {

//        LOGGER.debug("Delete cart record by customer id and product id ({},{}) ", customerId, productId);
//        LOGGER.debug("delete({},{})", id, model);
        cartRecordService.delete(customerId, productId);

        return "redirect:/customers/{customer_id}/cart/products";
    }

    ///////////////////////  ^^^^CART

    @GetMapping("/{customer_id}/edit")
    public String goToCustomerEditPage(
            @PathVariable("customer_id") Integer customerId,
            Principal principal,
            Model model) {

        System.out.println("/{customer_id}/edit");

        if (customerId != Integer.valueOf(getCustomerId(principal))) {
            return "redirect:customers/{customer_id}/edit";
        }

        model.addAttribute("customer_id", customerId);
        model.addAttribute("customer", customerService.findById(customerId).get());

        return "edit_customer";
    }

    @PatchMapping("/{customer_id}/edit")
    public String updateCustomer(
            @ModelAttribute("customer") @Valid Customer customer,
            BindingResult bindingResult,
            @PathVariable("customer_id") Integer customerId,
            Model model) {

        model.addAttribute("customer_id", customerId);

        if (bindingResult.hasErrors())
            return "edit_customer";

        customer.setCustomerId(customerId);
        customer.setRole(Role.ROLE_USER);
        customer.setIsActual(true);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerService.update(customer);

        return "redirect:/customers/{customer_id}/products";
    }
}
