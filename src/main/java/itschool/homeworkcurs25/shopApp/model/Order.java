package itschool.homeworkcurs25.shopApp.model;

import itschool.homeworkcurs25.shopApp.repository.ProductRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private double price;
    private Status status;

    public Order() {
        status = Status.PENDING;
    }

    public Order(Integer productId, Integer quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.status = Status.PENDING;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.price, price) == 0 &&
                Objects.equals(orderId, order.orderId) &&
                Objects.equals(productId, order.productId) &&
                Objects.equals(quantity, order.quantity) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, quantity, price, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
