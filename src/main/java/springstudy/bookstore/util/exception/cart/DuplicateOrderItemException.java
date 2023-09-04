package springstudy.bookstore.util.exception.cart;

public class DuplicateOrderItemException extends RuntimeException {

    public DuplicateOrderItemException(String message) {
        super(message);
    }
}
