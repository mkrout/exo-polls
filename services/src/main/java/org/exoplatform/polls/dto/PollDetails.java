package org.exoplatform.polls.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PollDetails {
    private PollDTO poll;
    private ArrayList<Question_Resoponses> questions;
}
