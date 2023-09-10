package springstudy.bookstore.domain.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class LoginRequest {
    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    @Builder
    public LoginRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
