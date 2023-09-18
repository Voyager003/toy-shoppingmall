package toy.shoppingmall.domain.user.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import toy.shoppingmall.domain.user.dao.AuthorityRepository;
import toy.shoppingmall.domain.user.dao.UserRepository;
import toy.shoppingmall.domain.user.domain.Authority;
import toy.shoppingmall.domain.user.domain.Role;
import toy.shoppingmall.domain.user.domain.User;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserApiLoginTest {

    @Autowired UserRepository userRepository;

    @Autowired AuthorityRepository authorityRepository;

    @Autowired MockMvc mockMvc;

    @Autowired WebApplicationContext context;

    @Autowired PasswordEncoder passwordEncoder;

    @Autowired toy.shoppingmall.global.jwt.JwtUtils jwtUtils;

    @Test
    @Transactional
    @DisplayName("로그인 성공 테스트")
    public void UserLoginTest1() throws Exception {
        /**
         * given : 회원가입 후, 로그인을 시도할 때,
         * when : 회원가입 시 저장된 정보와 일치한다면,
         * then : STATUS 200을 반환한다.
         */

        registerUser();

        ResultActions result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"a@example.com\", \"password\": \"1q2w3e4r\"}"));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("a@example.com"))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void UserLoginTest2() throws Exception {
        /**
         * given : 회원가입 후, 로그인을 시도할 때,
         * when : 회원가입 시 저장된 정보가 일치하지 않으면
         * then : STATUS 409를 반환하고, 예외가 발생한다.
         */

        registerUser();

        ResultActions result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"nonexistent@example.com\", \"password\": \"1q2w3e4r\"}"));

        result.andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorCode").value(409))
                .andExpect(jsonPath("$.errorMessage").value("존재하지 않는 이메일 입니다."))
                .andDo(print());
    }

    private void registerUser() {
        Set<Authority> authorities = new HashSet<>();
        Authority authority = Authority.builder().name(Role.ROLE_SELLER).build();
        authorityRepository.save(authority);

        String encodedPassword = passwordEncoder.encode("1q2w3e4r");
        userRepository.save(User.builder()
                .email("a@example.com")
                .password(encodedPassword)
                .roles(authorities)
                .build());
    }
}
