package toy.shoppingmall.global.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import toy.shoppingmall.domain.user.domain.Authority;
import toy.shoppingmall.domain.user.domain.Role;
import toy.shoppingmall.domain.user.dao.AuthorityRepository;
import toy.shoppingmall.domain.user.dao.UserRepository;
import toy.shoppingmall.domain.user.domain.User;
import toy.shoppingmall.global.security.UserDetailsImpl;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class jwtTokenTest {

    @Autowired UserRepository userRepository;
    @Autowired AuthorityRepository authorityRepository;
    @Autowired JwtUtils jwtUtils;
    @Autowired JwtProperties jwtProperties;

    @Test
    @DisplayName("유저 정보를 받아 토큰을 생성한다.")
    public void generateTokenTest() {

        Key key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));

        Set<Authority> authorities = new HashSet<>();
        Authority authority = Authority.builder().name(Role.ROLE_SELLER).build();
        authorityRepository.save(authority);

        User user = userRepository.save(User.builder()
                .email("ex@gmail.com")
                .password("1234")
                .roles(authorities)
                .build());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserDetailsImpl(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRoles().stream()
                                .map(auth -> new SimpleGrantedAuthority(auth.getName().name()))
                                .collect(Collectors.toList())
                ),
                null
        );

        String token = jwtUtils.issueJwtToken(authentication);

        Long userId = Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("토큰이 만료된 경우 유효성 검증에 실패한다.")
    public void validateExpirationTokenTest() throws Exception {

        String token = Jwts.builder()
                .setExpiration(new Date((new Date()).getTime() - 1000))
                .compact();

        boolean result = jwtUtils.validateJwtToken(token);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("토큰이 유효하다면 유효성 검증에 성공한다.")
    public void passValidateToken() throws Exception {

        String token = Jwts.builder().compact();

        boolean result = jwtUtils.validateJwtToken(token);
        assertThat(result).isTrue();
    }
}