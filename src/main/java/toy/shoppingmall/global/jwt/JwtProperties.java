package toy.shoppingmall.global.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String jwtCookieName;
    private String secretKey;
    private int expiration;

    public JwtProperties(String jwtCookieName, String secretKey, int expiration) {
        this.jwtCookieName = jwtCookieName;
        this.secretKey = secretKey;
        this.expiration = expiration;
    }
}
