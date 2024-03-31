package fr.pelliculum.restapi.answer;

import fr.pelliculum.restapi.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {


    @Query("SELECT new fr.pelliculum.restapi.answer.AnswerDTO(a.id, a.comment, a.reviewId, a.user, a.createdAt, a.spoiler) FROM Answer a WHERE a.reviewId = :reviewId")
    List<AnswerDTO> findAnswerDTOsByReviewId(Long reviewId);


    @Query("SELECT new fr.pelliculum.restapi.answer.AnswerDTO(a.id, a.comment, a.reviewId, a.user, a.createdAt, a.spoiler) FROM Answer a WHERE a.user.username = :username")
    List<AnswerDTO> findAnswerDTOsByUsername(String username);






}
