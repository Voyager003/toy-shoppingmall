package toy.shoppingmall.domain.user.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.shoppingmall.domain.user.dao.UserRepository;
import toy.shoppingmall.domain.user.dto.LoginRequest;
import toy.shoppingmall.global.jwt.JwtResponse;
import toy.shoppingmall.global.jwt.JwtUtils;
import toy.shoppingmall.global.security.UserDetailsImpl;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserLoginApi {

    @Autowired AuthenticationManager authenticationManager;

    @Autowired UserRepository userRepository;

    @Autowired PasswordEncoder passwordEncoder;

    @Autowired JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        validateLoginRequest(loginRequest);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.issueJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        roles)
        );
    }

    private void validateLoginRequest(LoginRequest loginRequest) {
        if (!userRepository.existsByEmail(loginRequest.getEmail())) {
            throw new IllegalArgumentException("존재하지 않는 이메일입니다.");
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), userRepository.findByEmail(loginRequest.getEmail()).get().getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
