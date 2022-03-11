package by.brest.karas.service.web_app;

import by.brest.karas.model.CartRecord;
import by.brest.karas.model.Product;
import by.brest.karas.model.dto.CartRecordDto;
import by.brest.karas.service.CartRecordService;
import by.brest.karas.service.CartRecordDtoService;
import by.brest.karas.service.CustomerService;
import by.brest.karas.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * Product controller.
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

    private final ProductService productService;

    private final CustomerService customerService;

    private final CartRecordService cartRecordService;

    private final CartRecordDtoService cartRecordDtoService;

    public AdminController(ProductService productService, CustomerService customerService, CartRecordService cartRecordService, CartRecordDtoService cartRecordDtoService) {
        this.productService = productService;
        this.customerService = customerService;
        this.cartRecordService = cartRecordService;
        this.cartRecordDtoService = cartRecordDtoService;
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

    /////////////////////// PRODUCTS
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

    @GetMapping("/{admin_id}/products/new")
    public String goToNewProductForm(@ModelAttribute Product product) {
        return "new_product_form";
    }

    @PostMapping("/{admin_id}/products/new")
    public String createProduct(
            @ModelAttribute("product") @Valid Product product
            , BindingResult bindingResult
            , Model model
            , Principal principal) {

        if (bindingResult.hasErrors()) {
            return "new_product_form";
        }

        Integer adminId = getPrincipalId(principal);

        product.setPicture("add picture");
        product.setChangedBy(adminId);
        productService.create(product);

        return "redirect:/admins/" + adminId + "/products";
    }



    /////////////////////// CUSTOMERS
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

        List<CartRecordDto> cartRecordDtos = cartRecordDtoService.findCartRecordDtosByCustomerId(adminId, filter);
        model.addAttribute("filter", filter);
        model.addAttribute("cart_lines", cartRecordDtos);
        model.addAttribute("cart_sum_total", (cartRecordDtos.size() != 0) ? cartRecordDtoService.findCartRecordDtosSumByCustomerId(adminId, filter) : "0.00");

        return "cart";
    }

    @GetMapping("/{admin_id}/cart/products/{product_id}/new")
    public String goToNewCartRecordForm(
            @ModelAttribute("cartRecord") CartRecord cartRecord
            , @PathVariable("product_id") Integer productId
            , Model model) {

        Product product = productService.findById(productId);
        model.addAttribute("productId", productId);
        model.addAttribute("productDescription", product.getShortDescription());
        model.addAttribute("productPrice", product.getPrice());
        model.addAttribute("cartRecord", cartRecord);

        return "new_cart_record_form";
    }

    @PostMapping("/{admin_id}/cart/products/{product_id}")
    public String createCartRecord(
            @ModelAttribute("cartRecord") @Valid CartRecord cartRecord
            , BindingResult bindingResult
            , @PathVariable("product_id") Integer productId
            , @PathVariable("admin_id") Integer adminId
            , Model model) {

        model.addAttribute("productId", productId);

        if (bindingResult.hasErrors()) {
            return "new_cart_record_form";
        }

        cartRecord.setCustomerId(adminId);
        cartRecord.setProductId(productId);
        cartRecordService.create(cartRecord);

        return "redirect:/admins/{admin_id}/cart/products";

    }
}
