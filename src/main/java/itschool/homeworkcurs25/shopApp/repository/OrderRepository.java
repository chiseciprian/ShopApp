package itschool.homeworkcurs25.shopApp.repository;

import itschool.homeworkcurs25.shopApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByOrderId(Integer orderId);

    Boolean existsByOrderId(Integer orderId);
}
