package org.exoplatform.polls.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Question_Resoponses {
    private QuestionDTO question;
    private ArrayList<ResponseDTO> responses;
}
