package toy.shoppingmall.user;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.model.Role;
import toy.shoppingmall.domain.user.Application.UserSignupService;
import toy.shoppingmall.domain.user.dao.UserRepository;
import toy.shoppingmall.domain.user.domain.User;
import toy.shoppingmall.domain.user.dto.UserSignupRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserSignupTest {

    @Autowired UserSignupService userSignupService;
    @Autowired UserRepository userRepository;
    @Autowired BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    public void UserSignupTest1() throws Exception {

        // Given
        String email = "example@example.com";
        String password = "password123";
        Role role = Role.SELLER;

        // When
        UserSignupRequest request = UserSignupRequest.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();

        userSignupService.Signup(request);
        User user = request.toEntity();

        // Then
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }
}
