package springstudy.bookstore.util.exception.item;

public class NotFoundItemException extends IllegalArgumentException {
    public NotFoundItemException(String message) {
        super(message);
    }
}

