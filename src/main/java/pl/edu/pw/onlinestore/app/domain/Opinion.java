package pl.edu.pw.onlinestore.app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @JoinColumn(name="receiver_id")
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sender_id")
    private User sender;

    @Column
    private int rating;

    @Column
    private String description;

    @Column
    private LocalDateTime dateAdded;

    public Opinion(User receiver, User sender, int rating, String description, LocalDateTime dateAdded) {
        this.receiver = receiver;
        this.sender = sender;
        this.rating = rating;
        this.description = description;
        this.dateAdded = dateAdded;
    }
}
