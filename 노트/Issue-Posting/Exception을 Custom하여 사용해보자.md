
### Custom한 Exception을 사용하여 관리
+ 기존에 BindingResult로 예외 처리를 했던 부분을 custom exception를 반환하도록 개선하였다.
+ @ExceptionHandler는 @Controller, @RestController 상관없이 1개의 Controller에서 발생하는 예외를 하나의 메서드에서 처리할 수 있게 한다.
+ custom exception으로 개선하여 의미있는 에러 메시지를 받을 수 있게 되었다.

### 의미있는 예외 정보를 반환
CustomException으로 구현하여 해당 에러와 관련된 메세지에 추가로 HttpStatus 또한 전달하도록 한다.
예를 들어 중복된 상품에 대한 예외처리를 정의해서, <br>
```java
public class DuplicateItemException extends IllegalArgumentException {
    public DuplicateItemException(String message) {
        super(message);
    }
}
```
itemService 메서드에서 아래처럼 에러 메시지를 정의한다.
```java
    // 중복된 상품 등록인지 확인하는 로직 ; 상품 이름, 상품 카테고리가 동일한 상품 등록인지?
    @Transactional(readOnly = true)
    public void validateDuplicateItem(CreateItemRequest dto) {
        if (itemRepository.existsByNameAndCategoryType(dto.getName(), CategoryType.valueOf(dto.getCategoryType()))) {
            throw new DuplicateItemException("이미 등록된 상품이 존재합니다.");
        }
    }
```

<br>

### @RestControllerAdvice를 이용한 Spring 예외 처리
+ 비즈니스 로직의 검증 결과를 bindingResult에 reject하여 추가하지 않고 <br>
+ @ExceptionHandle, @RestControllerAdvice를 통해 throw한 Exception을 처리하여 ResponseEntity<String>로 반환하여 HTTP 상태 코드, 에러 메시지를 받도록 한다. <br>

```java
package springstudy.bookstore.util.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springstudy.bookstore.util.exception.cart.DuplicateOrderItemException;
import springstudy.bookstore.util.exception.cart.NotFoundOrderItemException;
import springstudy.bookstore.util.exception.item.DuplicateItemException;
import springstudy.bookstore.util.exception.item.NotEnoughStockException;
import springstudy.bookstore.util.exception.item.NotFoundImgFileException;
import springstudy.bookstore.util.exception.item.NotFoundItemException;
import springstudy.bookstore.util.exception.sales.NotFoundSalesException;
import springstudy.bookstore.util.exception.user.DuplicateLoginIdException;
import springstudy.bookstore.util.exception.user.NotFoundUserException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

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
```

<br>

