package toy.shoppingmall.domain.item.domain;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorColumn(name = "B")
public class Book extends Item {
    private String author;
}
