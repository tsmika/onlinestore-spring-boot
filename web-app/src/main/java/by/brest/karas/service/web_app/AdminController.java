package by.brest.karas.service.web_app;

import by.brest.karas.service.CartRecordService;
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

    private final CartRecordService cartRecordService;

    public AdminController(ProductService productService, CustomerService customerService, CartRecordService cartRecordService) {
        this.productService = productService;
        this.customerService = customerService;
        this.cartRecordService = cartRecordService;
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

    @ModelAttribute("principal_role")
    public String getPrincipalRole() {
        return "admin";
    }

    @GetMapping("/{admin_id}/products")
    public String goToProductPage(
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

    @GetMapping(value = "/{admin_id}/products/{product_id}")
    public String goToProductPage(
            @PathVariable(value = "product_id") Integer productId,
            Model model) {

        model.addAttribute("product", productService.findById(productId));

        return "product_info";
    }

    @GetMapping(value = "/{admin_id}/customers")
    public String goToCustomerPage(
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            Model model) {

        model.addAttribute("filter", filter);

        if (filter == null) {
            model.addAttribute("customers", customerService.findAll());
        } else model.addAttribute("customers", customerService.searchCustomersByLogin(filter));

        return "customers";
    }


    /////////////////////// CART
    @GetMapping("/{admin_id}/cart/products")
    public String goToCartPage(
            @PathVariable("admin_id") Integer adminId,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
            Model model) {

        model.addAttribute("filter", filter);

        if (filter == null) {
            model.addAttribute("cart", cartRecordService.findCartByUserId(adminId));
        } else model.addAttribute("cart", cartRecordService.findFilteredCartByUserId(adminId, filter));

        return "admin/users/cart";
    }

}
