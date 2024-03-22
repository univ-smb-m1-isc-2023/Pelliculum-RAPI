package fr.pelliculum.restapi.review;


import fr.pelliculum.restapi.configuration.handlers.Response;
import fr.pelliculum.restapi.entities.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * Get all reviews for a film
     * @param movieId {@link Long} movieId
     * @return {@link List<Review>} reviews
     */

    public ResponseEntity<Object> getReviewsByMovieId(Long movieId) {
        List<ReviewDTO> reviews = reviewRepository.findReviewDTOsByMovieId(movieId);
        return Response.ok("Reviews for movieId: " + movieId, reviews);
    }

    /**
     * Get all reviews for a user
     * @param username {@link String} username
     * @return {@link List<Review>} reviews
     */
    public ResponseEntity<Object> getReviewsByUsername(String username) {
        List<ReviewDTO> reviews = reviewRepository.findReviewDTOsByUsername(username);
        return Response.ok("Reviews for username: " + username, reviews);
    }

    /**
     * Update a review
     * @param reviewId {@link Long} reviewId
     * @param review {@link Review} review
     * @return {@link Review} review
     */
    public ResponseEntity<Object> updateReview(Long reviewId, Review review) {
        Optional<Review> reviewOpt = reviewRepository.findById(reviewId);

        if (reviewOpt.isEmpty()) {
            return Response.error("Review not found !");
        }

        Review reviewToUpdate = reviewOpt.get();
        reviewToUpdate.setRating(review.getRating());
        reviewToUpdate.setComment(review.getComment());
        reviewRepository.save(reviewToUpdate);
        ReviewDTO reviewDTO = new ReviewDTO(reviewToUpdate);
        return Response.ok("Review successfully updated !", reviewDTO);

    }

    /**
     * Delete a review
     * @param reviewId {@link Long} reviewId
     * @return {@link Review} review
     */
    public ResponseEntity<Object> deleteReview(Long reviewId) {
        Optional<Review> reviewOpt = reviewRepository.findById(reviewId);

        if (reviewOpt.isEmpty()) {
            return Response.error("Review not found !");
        }

        reviewRepository.deleteById(reviewId);
        return Response.ok("Review successfully deleted !");
    }
}
