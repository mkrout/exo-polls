package org.exoplatform.polls.dto;

import com.sun.tools.javac.parser.Tokens;
import lombok.Data;
import org.exoplatform.polls.entity.QuestionEntity;
import org.exoplatform.polls.entity.ResponseEntity;

import java.io.Serializable;
import java.util.Date;

@Data


public class UserResponseDTO implements Serializable {
    private Long id;
    private String userName;
    private String comment;
    private QuestionEntity questionEntity;
    private ResponseEntity responseEntity;

}
