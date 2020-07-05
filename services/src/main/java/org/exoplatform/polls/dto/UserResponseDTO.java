package org.exoplatform.polls.dto;

import lombok.Data;
import java.io.Serializable;

@Data


public class UserResponseDTO implements Serializable {
    private Long id;
    private String userName;
    private QuestionDTO questionDto;
    private ResponseDTO pollResponseDto;
    private PollDTO pollDTO;

}
