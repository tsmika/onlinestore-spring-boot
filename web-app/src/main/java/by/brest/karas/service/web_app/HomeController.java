package by.brest.karas.service.web_app;


import by.brest.karas.model.Customer;
import by.brest.karas.model.Product;
import by.brest.karas.model.Role;
import by.brest.karas.service.CustomerService;
import by.brest.karas.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

/**
 * Root controller.
 */
@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    private final ProductService productService;

    private final CustomerService customerService;

    public HomeController(ProductService productService, CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
    }

    @GetMapping(value = "/")
    public String goToStartPage() {
        return "redirect:products";
    }

    @GetMapping(value = "/products")
    public String goToStartPage(
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter
            , @RequestParam(value = "view", required = false, defaultValue = "") String view
            , Model model
            , Principal principal) {

        model.addAttribute("filter", filter);
        model.addAttribute("view", view);
        model.addAttribute("products", productService.findAll());
        model.addAttribute("principal", "guest");

        if (filter == null) {
            model.addAttribute("products", productService.findAll());
        } else model.addAttribute("products", productService.findProductsByDescription(filter));

        return "products";
    }

    @GetMapping("/hello")
    public String authorisation() {
        return "hello_page";
    }

    @GetMapping("/auth")
    public String authorisation(Principal principal) {
        Customer customer = customerService.findByLogin(principal.getName());
        Integer principalId = customer.getCustomerId();

        if (customer.getRole().equals(Role.ROLE_ADMIN))
            return "redirect:/admins/" + principalId + "/products";

        return "redirect:/users/" + principalId + "/products";
    }

    //    @GetMapping(value = "/customers")
//    public String index(
//            @RequestParam(value = "filter", required = false, defaultValue = "") String filter
//            , Model model) {
//
//        model.addAttribute("filter", filter);
//
//        if (filter == null) {
//            model.addAttribute("customers", customerService.findAll());
//        } else model.addAttribute("customers", customerService.selectCustomers(filter));
//
//        return "customer_index";
//    }
//    @GetMapping(value = "/")
//    public String hello(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
//                        Model model) {
//
//        LOGGER.debug("hello(name:{})", name);
//        model.addAttribute("name", name);
//        return "user_index";
//    }

//    @GetMapping(value = "/")
//    public String defaultPageRedirect() {
//        return "redirect:hello";
//    }
}
