package toy.shoppingmall.domain.item.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@DiscriminatorValue("B")
@Getter
public class Book extends Item {
    private String author;
}
