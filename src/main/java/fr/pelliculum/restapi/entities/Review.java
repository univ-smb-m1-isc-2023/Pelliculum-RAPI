package fr.pelliculum.restapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String comment;
    private Double rating;

    private boolean spoiler;

    @Column(name = "movie_id")
    private Long movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Column(name = "likes")
    @ManyToMany
    private List<User> likes = new ArrayList<>();

    @Column(name = "answers")
    @OneToMany
    private List<Answer> answers = new ArrayList<>();
}
