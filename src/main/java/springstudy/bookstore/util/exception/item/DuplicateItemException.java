package springstudy.bookstore.util.exception.item;

public class DuplicateItemException extends IllegalArgumentException {
    public DuplicateItemException(String message) {
        super(message);
    }
}