package springstudy.bookstore.util.exception;

public class DuplicateOrderItemException extends RuntimeException {

    public DuplicateOrderItemException(String message) {
        super(message);
    }
}
