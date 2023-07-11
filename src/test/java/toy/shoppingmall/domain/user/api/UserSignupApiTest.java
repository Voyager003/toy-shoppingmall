package toy.shoppingmall.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import toy.shoppingmall.domain.model.Role;
import toy.shoppingmall.domain.user.application.UserSignupService;
import toy.shoppingmall.domain.user.dto.SignupRequest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserSignupApi.class)
@WithMockUser
class UserSignupApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserSignupService userSignupService;

    @Test
    @DisplayName("회원가입 시, 정보를 모두 받는다면 status 200을 반환한다.")
    public void UserSignupApiTest1() throws Exception {

        SignupRequest request = SignupRequest.builder()
                .email("ex@gmail.com")
                .password("123").
                role(Role.ROLE_CUSTOMER).build();

        mockMvc.perform(post("/signup")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("입력 값이 누락된다면 400 BAD_REQUEST를 반환한다.")
    public void UserSignupApiTest2() throws Exception {

        SignupRequest request = SignupRequest.builder()
                .email("ex@gmail.com")
                .role(Role.ROLE_CUSTOMER).build();

        mockMvc.perform(post("/signup")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}