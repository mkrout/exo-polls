package org.exoplatform.polls.entity;

import lombok.Data;
import org.exoplatform.commons.api.persistence.ExoEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "ResponseEntity")
@ExoEntity
@Table(name = "ADDONS_POLL_RESPONSE")
@Data
public class UserResponseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_ADDONS_POLL_USER_RESPONSE_ID", sequenceName = "SEQ_ADDONS_POLL_USER_RESPONSE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ADDONS_POLL_USER_RESPONSE_ID")
    @Column(name = "ID")
    protected Long id;

    @Column(name = "USER_NAME")
    protected String userName;

    @Column(name = "COMMENT")
    protected String COMMENT;
    @ManyToOne
    @JoinColumn(name = "POLL_QUESTION_ID")
    private QuestionEntity questionEntity;
    @ManyToOne
    @JoinColumn(name = "POLL_RESPONSE_ID")
    private ResponseEntity responseEntity;

    public UserResponseEntity() {
    }

    public UserResponseEntity(String userName,QuestionEntity questionEntity,  ResponseEntity responseEntity) {
        this.userName = userName;
        this.questionEntity = questionEntity;
        this.responseEntity = responseEntity;
    }
}

