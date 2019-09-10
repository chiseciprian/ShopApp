package itschool.homeworkcurs25.shopApp.controller;

import itschool.homeworkcurs25.shopApp.model.Category;
import itschool.homeworkcurs25.shopApp.model.Product;
import itschool.homeworkcurs25.shopApp.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) Category category) {
        return productService.getAll(category);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("{productId}")
    public Product updateProduct(@PathVariable Integer productId, @RequestBody Product product) {
        return productService.updateProduct(productId, product);
    }

    @DeleteMapping("{productId}")
    public void deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
    }
}
