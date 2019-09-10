package itschool.homeworkcurs25.shopApp;

import itschool.homeworkcurs25.shopApp.model.Category;
import itschool.homeworkcurs25.shopApp.model.Order;
import itschool.homeworkcurs25.shopApp.model.Product;
import itschool.homeworkcurs25.shopApp.model.Status;
import itschool.homeworkcurs25.shopApp.repository.OrderRepository;
import itschool.homeworkcurs25.shopApp.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static itschool.homeworkcurs25.shopApp.model.Category.ELECTRONICS;
import static itschool.homeworkcurs25.shopApp.model.Category.FOOD;
import static itschool.homeworkcurs25.shopApp.model.Status.ACCEPTED;
import static itschool.homeworkcurs25.shopApp.model.Status.PENDING;

@SpringBootApplication
public class ShopAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopAppApplication.class, args);
    }

    @Bean
    CommandLineRunner atStartup(ProductRepository repo, OrderRepository orderRepository) {
        return args -> {
            repo.saveAll(List.of(
                    new Product("Iphone X", ELECTRONICS, 999.99, 99),
                    new Product("Huawei", ELECTRONICS, 800.99, 5),
                    new Product("Milk", FOOD, 3.99, 100)
            ));

            orderRepository.saveAll(List.of(
                    new Order(1, 1, 999.99),
                    new Order(3, 5, 3.99)
            ));
        };
    }
}
