package fr.pelliculum.restapi.review;

import fr.pelliculum.restapi.entities.Review;
import fr.pelliculum.restapi.entities.User;
import fr.pelliculum.restapi.user.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class ReviewDTO {

    private Long id;
    private String comment;
    private Long rating;
    private Long movieId;
    private User user;
    private Timestamp createdAt;
    private Boolean spoiler;
    private List<String> likes;
    private List<String> answers;

    public ReviewDTO(Long id, String comment, Long rating, Long movieId, User user, Timestamp createdAt, Boolean spoiler) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.movieId = movieId;
        this.user = user;
        this.createdAt = createdAt;
        this.spoiler = spoiler;
    }

    public ReviewDTO(Review review) {
        this.comment = review.getComment();
        this.rating = review.getRating();
        this.movieId = review.getMovieId();
        this.createdAt = review.getCreatedAt();
        this.id = review.getId();
        this.spoiler = review.isSpoiler();
    }

}
