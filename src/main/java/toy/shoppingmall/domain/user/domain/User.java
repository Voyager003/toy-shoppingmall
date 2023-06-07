package toy.shoppingmall.domain.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.shoppingmall.domain.model.Address;
import toy.shoppingmall.domain.model.Role;
import toy.shoppingmall.domain.order.domain.Order;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_info")
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String Password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

}
