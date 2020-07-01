package org.exoplatform.polls.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PollDetails {
    private PollDTO poll;
    private List<Question_Resoponses> questions;
}
