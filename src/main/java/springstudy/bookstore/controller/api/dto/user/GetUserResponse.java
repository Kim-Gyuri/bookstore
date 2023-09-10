package springstudy.bookstore.controller.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import springstudy.bookstore.domain.entity.User;

@Data
@AllArgsConstructor
public class GetUserResponse {
    private String loginId;
    private String password;
    private String email;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    public GetUserResponse(User user) {
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.name = user.getName();
        this.city = user.getAddress().getCity();
        this.street = user.getAddress().getStreet();
        this.zipcode = user.getAddress().getZipcode();
    }
}
