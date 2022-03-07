package by.brest.karas.service.web_app;

import by.brest.karas.model.dto.CartRecordDto;
import by.brest.karas.service.CartRecordService;
import by.brest.karas.service.CartRecordDtoService;
import by.brest.karas.service.CustomerService;
import by.brest.karas.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Product controller.
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final ProductService productService;

    private final CustomerService customerService;

    private final CartRecordService cartRecordService;

    private final CartRecordDtoService cartLineService;

    public CustomerController(ProductService productService, CustomerService customerService, CartRecordService cartRecordService, CartRecordDtoService cartService, CartRecordDtoService cartLineService) {
        this.productService = productService;
        this.customerService = customerService;
        this.cartRecordService = cartRecordService;
        this.cartLineService = cartLineService;
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

}
