package org.exoplatform.polls.entity;

import lombok.Data;
import org.exoplatform.commons.api.persistence.ExoEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "UserResponseEntity")
@ExoEntity
@Table(name = "ADDONS_POLL_USER_RESPONSE")
@Data
@NamedQueries({
        @NamedQuery(name = "UserResponseEntity.getResponsesByPoll", query = "SELECT response FROM UserResponseEntity response where response.pollEntity.id = :id "),
        @NamedQuery(name = "UserResponseEntity.countResponsesByUserAndPoll", query = "SELECT COUNT(response.id) FROM UserResponseEntity response where response.pollEntity.id = :id And response.userName = :userName "),


})

public class UserResponseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_ADDONS_POLL_USER_RESPONSE_ID", sequenceName = "SEQ_ADDONS_POLL_USER_RESPONSE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ADDONS_POLL_USER_RESPONSE_ID")
    @Column(name = "ID")
    protected Long id;

    @Column(name = "USER_NAME")
    protected String userName;

    @ManyToOne
    @JoinColumn(name = "POLL_QUESTION_ID")
    private QuestionEntity questionEntity;
    @ManyToOne
    @JoinColumn(name = "POLL_RESPONSE_ID")
    private PollResponseEntity pollResponseEntity;
    @ManyToOne
    @JoinColumn(name = "POLL_POLL_ID")
    private PollEntity pollEntity;

    public UserResponseEntity() {
    }

    public UserResponseEntity(String userName,QuestionEntity questionEntity,  PollResponseEntity pollResponseEntity,  PollEntity pollEntity) {
        this.userName = userName;
        this.questionEntity = questionEntity;
        this.pollResponseEntity = pollResponseEntity;
        this.pollEntity = pollEntity;
    }
}

