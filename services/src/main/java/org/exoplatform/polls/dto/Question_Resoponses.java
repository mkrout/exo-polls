package org.exoplatform.polls.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Question_Resoponses {
    private QuestionDTO question;
    private ResponseDTO response;
    private List<ResponseDTO> responses;
}
