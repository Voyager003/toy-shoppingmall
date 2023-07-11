package toy.shoppingmall.domain.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}