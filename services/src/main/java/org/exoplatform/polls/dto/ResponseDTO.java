package org.exoplatform.polls.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data


public class ResponseDTO implements Serializable {
    private Long id;
    private String response;
    private QuestionDTO question;

}
