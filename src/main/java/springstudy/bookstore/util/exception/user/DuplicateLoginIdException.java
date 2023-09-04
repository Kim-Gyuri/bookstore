package springstudy.bookstore.util.exception.user;

public class DuplicateLoginIdException extends IllegalArgumentException {
    public DuplicateLoginIdException(String message) {
        super(message);
    }
}
