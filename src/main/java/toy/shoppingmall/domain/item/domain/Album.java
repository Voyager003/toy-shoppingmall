package toy.shoppingmall.domain.item.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@DiscriminatorValue("A")
@SuperBuilder
@Getter
public class Album extends Item {
    private String artist;
}
