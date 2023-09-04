package springstudy.bookstore.controller.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserResponse {
    private String name;
    private String email;
    private String password;

}
