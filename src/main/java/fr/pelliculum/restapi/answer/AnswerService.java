package fr.pelliculum.restapi.answer;


import fr.pelliculum.restapi.configuration.handlers.Response;
import fr.pelliculum.restapi.entities.Answer;
import fr.pelliculum.restapi.entities.User;
import fr.pelliculum.restapi.user.UserRepository;
import fr.pelliculum.restapi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * Get all answers for a film
     *
     * @param reviewId {@link Long} movieId
     * @return {@link List<Answer>} reviews
     */

    public ResponseEntity<Object> getAnswersByReviewId(Long reviewId) {
        List<AnswerDTO> answers = answerRepository.findAnswerDTOsByReviewId(reviewId);

        for (AnswerDTO answerDTO : answers) {
            // Hypothétique méthode pour récupérer les UserDTO qui ont aimé cette critique
            List<String> likes = userRepository.findUserNamesByReviewIdNative(answerDTO.getId());
            System.out.println(likes);
            answerDTO.setLikes(likes); // Assurez-vous que ReviewDTO a une méthode pour définir les likes
        }
        return Response.ok("Answers for reviewId: " + reviewId, answers);
    }

    /**
     * Get all answers for a user
     *
     * @param username {@link String} username
     * @return {@link List<Answer>} reviews
     */
    public ResponseEntity<Object> getAnswersByUsername(String username) {
        List<AnswerDTO> answers = answerRepository.findAnswerDTOsByUsername(username);
        return Response.ok("Answers for username: " + username, answers);

    }

    /**
     * Update a review
     *
     * @param answerId {@link Long} reviewId
     * @param answer  {@link Answer} review
     * @return {@link Answer} review
     */
    public ResponseEntity<Object> updateAnswer(Long answerId, Answer answer) {
        Optional<Answer> answerOpt = answerRepository.findById(answerId);

        if (answerOpt.isEmpty()) {
            return Response.error("Answer not found !");
        }

        Answer answerToUpdate = answerOpt.get();
        answerToUpdate.setComment(answer.getComment());
        answerToUpdate.setSpoiler(answer.isSpoiler());
        answerRepository.save(answerToUpdate);
        AnswerDTO answerDTO = new AnswerDTO(answerToUpdate);
        return Response.ok("Answer successfully updated !", answerDTO);

    }

    /**
     * Delete an answer
     *
     * @param answerId {@link Long} answerId
     * @return {@link Answer} answer
     */

    public ResponseEntity<Object> deleteAnswer(Long answerId) {
        Optional<Answer> answerOpt = answerRepository.findById(answerId);

        if (answerOpt.isEmpty()) {
            return Response.error("Answer not found !");
        }

        answerRepository.deleteById(answerId);
        return Response.ok("Answer successfully deleted !");
    }

    /**
     * Like an answer
     *
     * @param username {@link String} username
     * @param answerId {@link Long} answerId
     * @return {@link Answer} answer
     */

    public ResponseEntity<Object> likeAnswer(String username, Long answerId) {
        Optional<Answer> answerOpt = answerRepository.findById(answerId);

        if (answerOpt.isEmpty()) {
            return Response.error("Answer not found !");
        }

        Answer answer = answerOpt.get();

        if (answer.getUser().getUsername().equals(username)) {
            return Response.error("You can't like your own answer !");
        }

        User user = userService.findByUsernameOrNotFound(username);
        if (answer.getLikes().contains(user)) {
            answer.getLikes().remove(user);
            answerRepository.save(answer);
            return Response.ok("Answer successfully unliked !");
        }

        answer.getLikes().add(user);
        answerRepository.save(answer);
        return Response.ok("Answer successfully liked !");
    }


}
