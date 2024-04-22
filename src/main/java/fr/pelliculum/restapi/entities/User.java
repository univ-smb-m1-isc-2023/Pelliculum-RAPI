package fr.pelliculum.restapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.pelliculum.restapi.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Setter
@Getter
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @Column(name = "follows")
    @ManyToMany
    private java.util.List<User> follows;

    // The watchlist of the user, just store the id of the movies
    @Column(name = "watchlist")
    @ElementCollection
    private Set<Long> watchlist;

    @JsonIgnore
    @Column(name = "reviews")
    @ManyToMany
    private java.util.List<Review> reviews;


    @ManyToMany
    @JoinTable(
            name = "user_likes_review",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private Set<Review> likedReviews = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_answers_review",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private java.util.List<Review> answeredReviews = new ArrayList<>();

    @Column(name = "profile_picture_path")
    private String profilePicturePath;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return java.util.List.of(new SimpleGrantedAuthority(role.name()));
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

