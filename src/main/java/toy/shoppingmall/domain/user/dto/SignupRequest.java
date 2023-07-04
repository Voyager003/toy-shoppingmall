package toy.shoppingmall.domain.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.shoppingmall.domain.model.Role;
import toy.shoppingmall.domain.user.domain.User;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class SignupRequest {

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotNull(message = "역할은 필수 입력 값입니다.")
    private Role role;

    @Builder
    private SignupRequest(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();
    }
}
