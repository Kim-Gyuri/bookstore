package springstudy.bookstore.util.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) { super(message); }
}
