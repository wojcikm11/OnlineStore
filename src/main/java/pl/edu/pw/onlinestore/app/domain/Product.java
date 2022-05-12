package pl.edu.pw.onlinestore.app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private String title;

    private double price;

    @Lob
    private byte[] photo;

    @ManyToMany(mappedBy = "wishList")
    private Set<User> usersWishLists = new HashSet<>();

    public Product(User user, Category category, String title, double price) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.price = price;
    }

    public Product(User user, Category category, String title, double price, byte[] photo) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.price = price;
        this.photo = photo;
    }

    public void removeCategory() {
        this.category = null;
    }
}
