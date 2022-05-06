package pl.edu.pw.onlinestore.app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Opinion {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
    private User sender;

    @Column
    private int rating;

    @Column
    private String description;

    @Column
    private Date dateAdded;
}
