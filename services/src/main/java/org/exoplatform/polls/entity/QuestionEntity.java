package org.exoplatform.polls.entity;



import lombok.Data;
import org.exoplatform.commons.api.persistence.ExoEntity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "QuestionEntity")
@ExoEntity
@Table(name = "ADDONS_POLL_QUESTION")
@Data
@NamedQueries({
        @NamedQuery(name = "QuestionEntity.getQuestionsByPoll", query = "SELECT question FROM QuestionEntity question where question.pollEntity.id = :id ") })

public class QuestionEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_ADDONS_POLL_QUESTION_ID" , sequenceName = "SEQ_ADDONS_POLL_QUESTION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ADDONS_POLL_QUESTION_ID")
    @Column(name = "ID")
    protected Long id;

    @Column(name = "QUESTION")
    protected String question;

    @ManyToOne
    @JoinColumn(name = "POLL_POLL_ID")
    private PollEntity pollEntity;

    public QuestionEntity() {
    }

    public QuestionEntity(String question) {
        this.question = question;
    }
}

