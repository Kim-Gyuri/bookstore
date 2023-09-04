package springstudy.bookstore.util.exception.item;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) { super(message); }
}
