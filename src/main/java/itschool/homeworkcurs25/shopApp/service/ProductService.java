package itschool.homeworkcurs25.shopApp.service;

import itschool.homeworkcurs25.shopApp.model.Category;
import itschool.homeworkcurs25.shopApp.model.Product;
import itschool.homeworkcurs25.shopApp.model.exception.ProductDeleteException;
import itschool.homeworkcurs25.shopApp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll(Category category) {
        final List<Product> productList = productRepository.findAll();
        return productList.stream()
                .filter(product -> category == null || product.getCategory().equals(category))
                .collect(toList());
    }

    public Product addProduct(Product product) {
        if (productExistByName(product)) {
            Product productExist = productRepository.findByName(product.getName());
            productExist.setStock(product.getStock());
            return productRepository.save(productExist);
        } else {
            return productRepository.save(product);
        }
    }

    public Product updateProduct(Integer productId, Product product) {
        if (productExistByName(product)) {
            Product updatedProduct = mergeProductStock(product, productRepository.findByProductId(productId));
            return productRepository.save(updatedProduct);
        } else {
            product.setProductId(productId);
            return productRepository.save(product);
        }
    }

    public void deleteProduct(Integer productId) {
        Product product = productRepository.findByProductId(productId);
        if (product.getStock() == 0) {
            productRepository.deleteById(productId);
        } else {
            throw new ProductDeleteException("You cannot delete a product in stock");
        }
    }

    private Product mergeProductStock(Product product, Product productExist) {
        int stock = product.getStock() + productExist.getStock();
        productExist.setStock(stock);
        return productExist;
    }

    private boolean productExistByName(Product product) {
        if (productRepository.findByName(product.getName()) != null) {
            return true;
        } else {
            return false;
        }
    }
}
