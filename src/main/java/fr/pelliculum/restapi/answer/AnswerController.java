package fr.pelliculum.restapi.answer;

import fr.pelliculum.restapi.entities.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answers/")
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping("{reviewId}")
    public ResponseEntity<?> getAnswersByReviewId(@PathVariable Long reviewId) {
        return answerService.getAnswersByReviewId(reviewId);
    }

    @GetMapping("user/{username}")
    public ResponseEntity<?> getAnswersByUsername(@PathVariable String username) {
        return answerService.getAnswersByUsername(username);
    }

    @PutMapping("{answerId}")
    public ResponseEntity<?> updateAnswer(@PathVariable Long answerId, @RequestBody Answer answer) {
        return answerService.updateAnswer(answerId, answer);
    }

    @DeleteMapping("{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long answerId) {
        return answerService.deleteAnswer(answerId);
    }

    @PutMapping("{username}/like/{answerId}")
    public ResponseEntity<?> likeAnswer(@PathVariable String username, @PathVariable Long answerId) {
        return answerService.likeAnswer(username, answerId);
    }

}
