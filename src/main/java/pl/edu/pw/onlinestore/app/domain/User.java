package pl.edu.pw.onlinestore.app.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private UserInfo userInfo;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Product> userProducts;

    @OneToMany(
            mappedBy = "receiver",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Opinion> receivedOpinions = new ArrayList<>();

    @OneToMany(
            mappedBy = "sender",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Opinion> givenOpinions = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            if (this.userInfo != null) {
                this.userInfo.setUser(null);
            }
        }
        else {
            userInfo.setUser(this);
        }
        this.userInfo = userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        SimpleGrantedAuthority userRole = new SimpleGrantedAuthority("ROLE_" + role.name());
        roles.add(userRole);
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
