package by.brest.karas.service.web_app;

import by.brest.karas.service.CustomerService;
import by.brest.karas.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Product controller.
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

    private final ProductService productService;

    private final CustomerService customerService;

    public AdminController(ProductService productService, CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
    }

    private Integer getPrincipalId(Principal principal) {
        return customerService.findByLogin(principal.getName()).get().getCustomerId();
    }

    @ModelAttribute("login")
    public String getAdminLogin(Principal principal) {
        return customerService.findById(getPrincipalId(principal)).get().getLogin();
    }

    @ModelAttribute("admin_id")
    public String getAdminId(Principal principal) {
        return getPrincipalId(principal).toString();
    }

//    @ModelAttribute("customer_id")
//    public String getnId() {
//        return "customer_id";
//    }

    @ModelAttribute("principal_role")
    public String getPrincipalRole() {
        return "admin";
    }

    @GetMapping("/{admin_id}/products")
    public String seeProducts(
            @PathVariable("admin_id") Integer adminId,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            @RequestParam(value = "view", required = false, defaultValue = "") String view,
            Model model) {

        model.addAttribute("filter", filter);
        model.addAttribute("view", view);
        model.addAttribute("admin_id", adminId);

        if (filter == null) {
            model.addAttribute("products", productService.findAll());
        } else model.addAttribute("products", productService.findProductsByDescription(filter));

        return "products";
    }

}
