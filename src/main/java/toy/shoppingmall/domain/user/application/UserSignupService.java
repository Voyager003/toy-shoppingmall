package toy.shoppingmall.domain.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.shoppingmall.domain.model.Authority;
import toy.shoppingmall.domain.model.Role;
import toy.shoppingmall.domain.user.dao.AuthorityRepository;
import toy.shoppingmall.domain.user.dao.UserRepository;
import toy.shoppingmall.domain.user.domain.User;
import toy.shoppingmall.domain.user.dto.SignupRequest;
import toy.shoppingmall.domain.user.exception.DuplicateEmailException;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserSignupService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void Signup(SignupRequest request) throws Exception {
        validateDuplicateEmail(request.getEmail());

        Set<Authority> roles = new HashSet<>();
        Authority authority = Authority.builder().name(request.getRole()).build();
        authorityRepository.save(authority);
        roles.add(authority);

        userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build());
    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}