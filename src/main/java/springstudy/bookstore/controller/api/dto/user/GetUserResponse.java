package springstudy.bookstore.controller.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserResponse {
    private String email;
    private String name;
    private String city;

    private int income;
}
