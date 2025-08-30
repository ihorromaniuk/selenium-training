package core.basesyntax.seleniumtraining.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "books")
public class Book {
    @Id
    private String id;

    private String title;

    private String imageUrl;

    private String description;

    private BigDecimal price;
}
