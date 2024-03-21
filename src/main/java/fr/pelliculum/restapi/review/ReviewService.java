package fr.pelliculum.restapi.review;


import fr.pelliculum.restapi.configuration.handlers.Response;
import fr.pelliculum.restapi.entities.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
