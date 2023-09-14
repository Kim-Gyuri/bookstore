package springstudy.bookstore.domain.dto.user;

import lombok.Data;
import springstudy.bookstore.domain.entity.Address;
import springstudy.bookstore.domain.entity.User;

import javax.validation.constraints.NotNull;

@Data
public class CreateUserRequest {

    @NotNull
    private String loginId;
    @NotNull
    private String password;
    private String name;
    private String email;
    private String city;
    private String street;
    private String zipcode;

    public Address addressEntity() {
        return Address.addressBuilder()
                .city(city)
                .street(street)
                .zipcode(zipcode)
                .build();
    }

    public User userEntity() {
        return User.userBuilder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .email(email)
                .address(addressEntity())
                .build();
    }
}
