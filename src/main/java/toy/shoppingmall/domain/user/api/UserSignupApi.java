package toy.shoppingmall.domain.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toy.shoppingmall.domain.user.application.UserSignupService;
import toy.shoppingmall.domain.user.dto.SignupRequest;

@RestController
@RequiredArgsConstructor
public class UserSignupApi {

    private final UserSignupService userSignupService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest request) throws Exception {
        userSignupService.Signup(request);
        return ResponseEntity.ok().build();
    }
}
