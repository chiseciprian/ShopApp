package itschool.homeworkcurs25.shopApp.controller;

import itschool.homeworkcurs25.shopApp.model.Order;
import itschool.homeworkcurs25.shopApp.model.Status;
import itschool.homeworkcurs25.shopApp.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAll(@RequestParam(required = false) Status status) {
        return orderService.getAll(status);
    }

    @PostMapping
    public Order addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @PatchMapping("{orderId}")
    public void changeStatus(@PathVariable Integer orderId) {
        orderService.changeStatus(orderId);
    }

    @DeleteMapping("{orderId}")
    public void deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
    }
}
