package itschool.homeworkcurs25.shopApp.model.exception;

public class OrderInvalidException extends RuntimeException {
    public OrderInvalidException(String message) {
        super(message);
    }
}
