package toy.shoppingmall.domain.item.domain;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorColumn(name = "A")
public class Album extends Item {
    private String artist;
}
