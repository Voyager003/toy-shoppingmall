package toy.shoppingmall.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "authorities")
@NoArgsConstructor
public class Authority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Role name;

    @Builder
    public Authority(Role name) {
        this.name = name;
    }
}
