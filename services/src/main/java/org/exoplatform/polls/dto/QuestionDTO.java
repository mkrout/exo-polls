package org.exoplatform.polls.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data

public class QuestionDTO implements Serializable {
    private Long id;
    private String question;
    private PollDTO poll;

}


