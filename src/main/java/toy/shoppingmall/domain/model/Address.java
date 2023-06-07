package toy.shoppingmall.domain.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 값 타입으로, 유저의 주소 정보를 가짐
 */
@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    private String country;
    private String city;

    public Address(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
