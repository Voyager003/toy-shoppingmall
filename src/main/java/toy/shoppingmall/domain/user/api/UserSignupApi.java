package toy.shoppingmall.domain.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.shoppingmall.domain.user.Application.UserSignupService;
import toy.shoppingmall.domain.user.dto.UserSignupRequest;

@RestController
@RequiredArgsConstructor
public class UserSignupApi {

    private final UserSignupService userSignupService;

    @PostMapping("/signup")
    public Long signup(@RequestBody @Valid UserSignupRequest request) throws Exception {
        return userSignupService.Signup(request);
    }
}
