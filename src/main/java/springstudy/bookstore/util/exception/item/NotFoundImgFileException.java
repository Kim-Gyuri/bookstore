package springstudy.bookstore.util.exception.item;

import java.io.IOException;

public class NotFoundImgFileException extends IOException {
    public NotFoundImgFileException(String message) {
        super(message);
    }
}

