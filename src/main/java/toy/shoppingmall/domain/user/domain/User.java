package toy.shoppingmall.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.shoppingmall.domain.model.Address;
import toy.shoppingmall.domain.model.Role;
import toy.shoppingmall.domain.order.domain.Order;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_info")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(name = "user_password", nullable = false)
    private String Password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public User(String nickname, String email, String password, Role role) {
        this.email = email;
        this.Password = password;
        this.role = role;
    }
}
