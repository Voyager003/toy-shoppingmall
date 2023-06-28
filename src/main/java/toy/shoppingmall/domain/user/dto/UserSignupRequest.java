package toy.shoppingmall.domain.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import toy.shoppingmall.domain.model.Role;
import toy.shoppingmall.domain.user.domain.User;

@Getter
@Builder
public class UserSignupRequest {

    @NotEmpty(message = "닉네임은 필수 입력 값입니다.")
    private String username;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    private Role role;

    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}
