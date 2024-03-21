package fr.pelliculum.restapi.review;


import fr.pelliculum.restapi.entities.Review;
import lombok.RequiredArgsConstructor;
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

    public List<ReviewDTO> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findReviewDTOsByMovieId(movieId);
    }


}
