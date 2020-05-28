package org.exoplatform.polls.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PollDTO implements Serializable {
    private Long id;
    private String name;
    private String userName;
    private String description;
    private Date createdDate;
    private Date publicationDate;
    private Date expirationDate;
    private String createdDateFormatted;
    private String publicationDateFormatted;
    private String expirationDateFormatted;
    private Boolean active;
}
