package itschool.homeworkcurs25.shopApp.repository;

import itschool.homeworkcurs25.shopApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByName(String name);

    Product findByProductId(Integer productId);

}
