package by.brest.karas.service.rest_app;

import by.brest.karas.model.Product;
import by.brest.karas.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/products")
    public Collection<Product> getProducts(){
        return productService.findAll();
    }
}
