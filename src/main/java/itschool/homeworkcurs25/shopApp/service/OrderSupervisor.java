package itschool.homeworkcurs25.shopApp.service;

import itschool.homeworkcurs25.shopApp.model.Order;
import itschool.homeworkcurs25.shopApp.model.Product;
import itschool.homeworkcurs25.shopApp.model.exception.OrderInvalidException;
import itschool.homeworkcurs25.shopApp.repository.OrderRepository;
import itschool.homeworkcurs25.shopApp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import static itschool.homeworkcurs25.shopApp.model.Status.*;

@Service
public class OrderSupervisor {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    public OrderSupervisor(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void changeStatus(Order order) {
        Product product = productRepository.findByProductId(order.getProductId());
        if (order.getStatus().equals(PENDING)) {
            acceptOrder(order, product);
        } else if (order.getStatus().equals(ACCEPTED)) {
            statusToShipped(order);
        } else if (order.getStatus().equals(SHIPPED)) {
            statusToDelivered(order);
        } else {
            throw new OrderInvalidException("Order cannot change status");
        }
    }

    public void cancelOrder(Order order) {
        Product product = productRepository.findByProductId(order.getProductId());
        if (order.getStatus().equals(PENDING) || order.getStatus().equals(ACCEPTED) || order.getStatus().equals(SHIPPED)) {
            changeStatusCancel(order, product);
        }
    }

    private void changeStatusCancel(Order order, Product product) {
        order.setStatus(CANCELED);
        orderRepository.save(order);
        product.setStock(product.getStock() + order.getQuantity());
        productRepository.save(product);
    }

    private void statusToDelivered(Order order) {
        order.setStatus(DELIVERED);
        orderRepository.save(order);
    }

    private void statusToShipped(Order order) {
        order.setStatus(SHIPPED);
        orderRepository.save(order);
    }

    private void acceptOrder(Order order, Product product) {
        if (checkQuantity(order, product)) {
            product.setStock(product.getStock() - order.getQuantity());
            productRepository.save(product);
            order.setStatus(ACCEPTED);
            orderRepository.save(order);
        }
    }

    private boolean checkQuantity(Order order, Product product) {
        if (product.getStock() - order.getQuantity() >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
