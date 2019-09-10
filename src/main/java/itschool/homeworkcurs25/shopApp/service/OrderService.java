package itschool.homeworkcurs25.shopApp.service;

import itschool.homeworkcurs25.shopApp.model.Order;
import itschool.homeworkcurs25.shopApp.model.Product;
import itschool.homeworkcurs25.shopApp.model.Status;
import itschool.homeworkcurs25.shopApp.model.exception.OrderInvalidException;
import itschool.homeworkcurs25.shopApp.repository.OrderRepository;
import itschool.homeworkcurs25.shopApp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static itschool.homeworkcurs25.shopApp.model.Status.*;
import static java.util.stream.Collectors.toList;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private OrderSupervisor orderSupervisor;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderSupervisor orderSupervisor) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderSupervisor = orderSupervisor;
    }

    public List<Order> getAll(Status status) {
        final List<Order> orderList = orderRepository.findAll();
        return orderList.stream()
                .filter(order -> status == null || order.getStatus().equals(status))
                .collect(toList());
    }

    public Order addOrder(Order order) {
        if (validateProductId(order.getProductId()) && validateQuantity(order)) {
            Product product = productRepository.findByProductId(order.getProductId());
            double price = product.getPrice() * order.getQuantity();
            order.setPrice(price);
            return orderRepository.save(order);
        } else {
            order.setStatus(REJECTED);
            orderRepository.save(order);
            throw new OrderInvalidException("Invalid Order");
        }
    }

    public void changeStatus(Integer orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        orderSupervisor.changeStatus(order);
    }

    public void deleteOrder(Integer orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        orderSupervisor.cancelOrder(order);
    }

    private boolean validateQuantity(Order order) {
        Product product = productRepository.findByProductId(order.getProductId());
        if ((product.getStock() - order.getQuantity()) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateProductId(Integer productId) {
        if (productRepository.findById(productId).isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}
