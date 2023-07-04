package toy.shoppingmall.domain.user.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.model.Role;
import toy.shoppingmall.domain.user.dao.UserRepository;
import toy.shoppingmall.domain.user.domain.User;
import toy.shoppingmall.domain.user.dto.SignupRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserSignupTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원 정보가 입력되면 회원가입 로직을 실행한다.")
    @Transactional
    public void SignupTest1() throws Exception {

        /**
         * Given : 이메일, 패스워드, 역할이 주어졌을 때
         * When : 회원가입 로직을 실행하여, DB에 정보를 저장
         * Then : DB에 저장된 정보와, 입력한 정보가 일치하는지 확인한다.
         */

        String email = "example@example.com";
        String password = "password123";
        Role role = Role.ROLE_SELLER;

        SignupRequest request = SignupRequest.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();

        User user = request.toEntity();
        userRepository.save(user);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }
}