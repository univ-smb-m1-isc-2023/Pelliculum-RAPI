package fr.pelliculum.restapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lists")
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "public", nullable = false)
    private Boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "movies")
    @ElementCollection
    private Set<Long> movies;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }





}
