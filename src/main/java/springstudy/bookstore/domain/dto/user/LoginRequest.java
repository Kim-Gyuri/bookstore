package springstudy.bookstore.domain.dto.user;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class LoginRequest {
    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
