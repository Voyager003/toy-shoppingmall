package toy.shoppingmall.domain.user.Application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.user.dao.UserRepository;
import toy.shoppingmall.domain.user.domain.User;
import toy.shoppingmall.domain.user.dto.UserSignupRequest;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserSignupService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long Signup(UserSignupRequest request) throws Exception {
        return userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build()).getId();
    }
}
