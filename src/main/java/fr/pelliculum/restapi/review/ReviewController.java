package fr.pelliculum.restapi.review;

import fr.pelliculum.restapi.entities.Answer;
import fr.pelliculum.restapi.entities.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews/")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("{movieId}")
    public ResponseEntity<?> getReviewsByMovieId(@PathVariable Long movieId) {
        return reviewService.getReviewsByMovieId(movieId);
    }

    @GetMapping("user/{username}")
    public ResponseEntity<?> getReviewsByUsername(@PathVariable String username) {
        return reviewService.getReviewsByUsername(username);
    }

    @PutMapping("{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
        return reviewService.updateReview(reviewId, review);
    }

    @DeleteMapping("{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        return reviewService.deleteReview(reviewId);
    }

    @PutMapping("{username}/like/{reviewId}")
    public ResponseEntity<?> likeReview(@PathVariable String username, @PathVariable Long reviewId) {
        return reviewService.likeReview(username, reviewId);
    }

    @PostMapping("{username}/answer/{reviewId}")
    public ResponseEntity<?> addAnswerToReview(@PathVariable String username, @PathVariable Long reviewId, @RequestBody Answer answer) {
        return reviewService.addAnswerToReview(username, reviewId, answer);
    }

}
