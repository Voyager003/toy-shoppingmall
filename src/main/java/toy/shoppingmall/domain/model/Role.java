package toy.shoppingmall.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    @JsonProperty("ROLE_SELLER")
    ROLE_SELLER("판매자"),

    @JsonProperty("ROLE_CUSTOMER")
    ROLE_CUSTOMER("구매자");

    private final String value;
}
