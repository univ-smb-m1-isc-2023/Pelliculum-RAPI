package fr.pelliculum.restapi.answer;

import fr.pelliculum.restapi.entities.Answer;
import fr.pelliculum.restapi.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class AnswerDTO {

    private Long id;
    private String comment;
    private Long reviewId;
    private User user;
    private Timestamp createdAt;
    private Boolean spoiler;
    private List<String> likes;

    public AnswerDTO(Long id, String comment, Long reviewId, User user, Timestamp createdAt, Boolean spoiler) {
        this.id = id;
        this.comment = comment;
        this.reviewId = reviewId;
        this.user = user;
        this.createdAt = createdAt;
        this.spoiler = spoiler;
    }

    public AnswerDTO(Answer answer) {
        this.comment = answer.getComment();
        this.reviewId = answer.getId();
        this.createdAt = answer.getCreatedAt();
        this.id = answer.getId();
        this.spoiler = answer.isSpoiler();
    }

}
