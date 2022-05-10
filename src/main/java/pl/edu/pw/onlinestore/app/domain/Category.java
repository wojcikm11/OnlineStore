package pl.edu.pw.onlinestore.app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Category {
    public static final String DEFAULT_TITLE = "Wszystkie produkty";
    public static final String DEFAULT_DESCRIPTION = "W naszym internetowym sklepie możesz znaleźć wszystko, co " +
            "mogli wystawić nasi użytkownicy - począwszy od atykułów szkolnych po smartfony. Sprawdź sam!";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Product> categoryProducts;

    public Category(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
