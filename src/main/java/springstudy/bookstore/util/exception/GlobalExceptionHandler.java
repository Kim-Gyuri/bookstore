package springstudy.bookstore.util.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springstudy.bookstore.util.exception.cart.DuplicateOrderItemException;
import springstudy.bookstore.util.exception.cart.NotFoundOrderItemException;
import springstudy.bookstore.util.exception.item.*;
import springstudy.bookstore.util.exception.sales.NotFoundSalesException;
import springstudy.bookstore.util.exception.user.DuplicateLoginIdException;
import springstudy.bookstore.util.exception.user.NotFoundUserException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ViolationItemException.class)
    public final ResponseEntity<String> checkViolationItemException(ViolationItemException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateLoginIdException.class)
    public final ResponseEntity<String> checkDuplicateLoginIdException(DuplicateLoginIdException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateItemException.class)
    public final ResponseEntity<String> checkDuplicateItemException(DuplicateItemException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateOrderItemException.class)
    public final ResponseEntity<String> checkDuplicateOrderItemException(DuplicateOrderItemException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundUserException.class)
    public final ResponseEntity<String> checkNotFoundUserException(NotFoundUserException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundImgFileException.class)
    public final ResponseEntity<String> checkNotFoundImgFileException(NotFoundImgFileException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundItemException.class)
    public final ResponseEntity<String> checkNotFoundItemException(NotFoundItemException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundOrderItemException.class)
    public final ResponseEntity<String> checkNotFoundOrderItemException(NotFoundOrderItemException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundSalesException.class)
    public final ResponseEntity<String> checkNotFoundSalesException(NotFoundSalesException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public final ResponseEntity<String> checkNotEnoughStockException(NotEnoughStockException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
