package org.exoplatform.polls.entity;

import lombok.Data;
import org.exoplatform.commons.api.persistence.ExoEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "ResponseEntity")
@ExoEntity
@Table(name = "ADDONS_POLL_RESPONSE")
@Data
public class ResponseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_ADDONS_POLL_RESPONSE_ID", sequenceName = "SEQ_ADDONS_POLL_RESPONSE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ADDONS_POLL_RESPONSE_ID")
    @Column(name = "ID")
    protected Long id;

    @Column(name = "RESPONSE")
    protected String response;
    @ManyToOne
    @JoinColumn(name = "POLL_QUESTION_ID")
    private QuestionEntity questionEntity;

    public ResponseEntity() {
    }

    public ResponseEntity(String response) {
        this.response = response;
    }
}

