package fr.pelliculum.restapi.review;

import fr.pelliculum.restapi.entities.Review;
import fr.pelliculum.restapi.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ReviewDTO {

    private Long id;
    private String comment;
    private Long rating;
    private Long movieId;
    private User user;
    private Timestamp createdAt;
    private Boolean spoiler;
    private Integer likes;

    public ReviewDTO(Long id, String comment, Long rating, Long movieId, User user, Timestamp createdAt, Boolean spoiler, Integer likes) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.movieId = movieId;
        this.user = user;
        this.createdAt = createdAt;
        this.spoiler = spoiler;
        this.likes = likes;
    }

    public ReviewDTO(Review review) {
        this.comment = review.getComment();
        this.rating = review.getRating();
        this.movieId = review.getMovieId();
        this.createdAt = review.getCreatedAt();
        this.id = review.getId();
        this.spoiler = review.isSpoiler();
        this.likes = review.getLikes().size();
    }

}
