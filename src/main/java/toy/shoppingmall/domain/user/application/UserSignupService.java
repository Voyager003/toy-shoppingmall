package toy.shoppingmall.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.user.dao.UserRepository;
import toy.shoppingmall.domain.user.domain.User;
import toy.shoppingmall.domain.user.dto.SignupRequest;
import toy.shoppingmall.domain.user.exception.DuplicateEmailException;


@Service
@RequiredArgsConstructor
public class UserSignupService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void Signup(SignupRequest request) throws Exception {
        validateDuplicateEmail(request.getEmail());
        userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build());
    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
