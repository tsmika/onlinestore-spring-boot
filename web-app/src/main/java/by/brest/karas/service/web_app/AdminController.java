package by.brest.karas.service.web_app;

import by.brest.karas.service.CustomerService;
import by.brest.karas.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/{admin_id}/products")
    public String seeProducts(
            @PathVariable("admin_id") Long adminId
            , @RequestParam(value = "filter", required = false, defaultValue = "") String filter
            , @RequestParam(value = "view", required = false, defaultValue = "") String view
            , Model model) {

        model.addAttribute("admin_id", adminId);
//        model.addAttribute("login", userDAO.getUserById(userId).getLogin());
        model.addAttribute("filter", filter);
        model.addAttribute("view", view);

        if (filter == null) {
            model.addAttribute("products", productService.findAll());
        } else model.addAttribute("products", productService.findProductsByDescription(filter));

        return "products";
    }

}
