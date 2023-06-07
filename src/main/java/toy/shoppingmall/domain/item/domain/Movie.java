package toy.shoppingmall.domain.item.domain;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorColumn(name = "M")
public class Movie extends Item {
    private String director;
}
