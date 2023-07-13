package toy.shoppingmall.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@ConfigurationProperties("jwt")
@AllArgsConstructor
public class JwtProperties {

    private String secretKey;
    private int expiration;
}
