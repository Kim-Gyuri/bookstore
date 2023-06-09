package springstudy.bookstore.domain.dto;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class LoginFormDto {
    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
