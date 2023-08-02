package toy.shoppingmall.domain.user.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.model.Authority;
import toy.shoppingmall.domain.model.Role;
import toy.shoppingmall.domain.user.dao.AuthorityRepository;
import toy.shoppingmall.domain.user.dao.UserRepository;
import toy.shoppingmall.domain.user.domain.User;
import toy.shoppingmall.domain.user.dto.SignupRequest;
import toy.shoppingmall.domain.user.exception.DuplicateEmailException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserSignupTest {

    @Autowired UserRepository userRepository;
    @Autowired AuthorityRepository authorityRepository;
    @Autowired UserSignupService userSignupService;

    @Test
    @DisplayName("회원가입 로직을 실행하고, null 여부 및 정보 일치를 확인한다.")
    @Transactional
    public void SignupTest1() throws Exception {

        /**
         * Given : 이메일, 패스워드, 권한이 주어졌을 때
         * When : 회원가입 로직을 실행하여, DB에 정보를 저장
         * Then : DB에 저장된 정보와, 입력한 정보가 일치하는지 확인한다.
         */

        String email = "example@example.com";
        String password = "1q2w3e4r";
        Role role = Role.ROLE_SELLER;

        SignupRequest request = SignupRequest.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();

        Authority authority = Authority.builder().name(role).build();
        authorityRepository.save(authority);

        User user = request.toEntity();
        userRepository.save(user);

        Optional<Authority> savedAuthority = authorityRepository.findByName(role);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(authority.getName(), savedAuthority.get().getName());
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입을 한다면, 예외를 발생시킨다.")
    @Transactional
    public void SignupTest2() throws Exception {

        /**
         * Given : 이메일, 패스워드, 역할이 주어졌을 때
         * When : 같은 정보로 회원가입 로직을 작성하여, DB에 정보를 저장
         * Then : DB에 동일한 이메일이 존재하므로, 예외를 발생시킨다.
         */

        String email = "example@example.com";
        String password = "password123";
        Role role = Role.ROLE_SELLER;

        SignupRequest request = SignupRequest.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();

        userSignupService.Signup(request);

        assertThrows(DuplicateEmailException.class, () -> {
            userSignupService.Signup(request);
        });
    }
}