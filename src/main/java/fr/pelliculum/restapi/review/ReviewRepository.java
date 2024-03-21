package fr.pelliculum.restapi.review;

import fr.pelliculum.restapi.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {




}
