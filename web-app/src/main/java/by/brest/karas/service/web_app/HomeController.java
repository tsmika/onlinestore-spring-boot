package by.brest.karas.service.web_app;

import by.brest.karas.model.Customer;
import by.brest.karas.model.Role;
import by.brest.karas.service.CustomerService;
import by.brest.karas.service.ProductService;
//import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Root controller.
 */
@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    private final ProductService productService;

    private final CustomerService customerService;

    private final BCryptPasswordEncoder passwordEncoder;

    public HomeController(ProductService productService, CustomerService customerService, BCryptPasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("principal_role")
    public String getPrincipalRole() {
        return "guest";
    }

    @GetMapping(value = "/")
    public String welcomePage() {
        return "redirect:products";
    }

    @GetMapping(value = "/products")
    public String goToStartPage(
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            @RequestParam(value = "view", required = false, defaultValue = "") String view,
            Model model,
            Principal principal) {

        for (Customer customer : customerService.findAll()) {
            customer.setPassword(passwordEncoder.encode("1"));
        }

        model.addAttribute("filter", filter);
        model.addAttribute("view", view);
        model.addAttribute("products", productService.findAll());

        if (filter == null) {
            model.addAttribute("products", productService.findAll());
        } else model.addAttribute("products", productService.findProductsByDescription(filter));

        return "products";
    }

    @GetMapping(value = "/products/{product_id}")
    public String goToProductPage(
            @PathVariable(value = "product_id") Integer productId,
            Model model) {

        model.addAttribute("product", productService.findById(productId));

        return "product_info";
    }

    @GetMapping("/auth")
    public String authorisation(Principal principal) {
        Customer customer = customerService.findByLogin(principal.getName()).get();
        Integer principalId = customer.getCustomerId();

        if (customer.getRole().equals(Role.ROLE_ADMIN))
            return "redirect:/admins/" + principalId + "/products";

        return "redirect:/customers/" + principalId + "/products";
    }


    @GetMapping("/login")
    public String getLogin(
            @RequestParam(value = "error", required = false) String error
            , Model model) {

//        LOGGER.debug("login (name:{})" );
        model.addAttribute("error", error != null);
        return "login";
    }

    @GetMapping("/new_customer")
    public String goToNewCustomerForm(@ModelAttribute("customer") Customer customer) {
        return "new_customer_form";
    }

    @PostMapping("/new_customer")
    public String createNewCustomer(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "new_customer_form";

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole(Role.ROLE_USER);
        customer.setIsActual(true);
        customerService.create(customer);

        return "redirect:products";
    }
}
