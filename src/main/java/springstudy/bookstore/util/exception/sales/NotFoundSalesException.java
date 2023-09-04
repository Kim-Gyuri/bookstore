package springstudy.bookstore.util.exception.sales;

public class NotFoundSalesException extends IllegalArgumentException {
    public NotFoundSalesException(String message) {
        super(message);
    }
}


