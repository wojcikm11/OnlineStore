package pl.edu.pw.onlinestore.app.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Entity
@Table(name = "user_info")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "user")
public class UserInfo implements Serializable {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String city;

    @Email
    @Column
    private String email;

    @Column
    private String phone;
}
