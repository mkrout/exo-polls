package org.exoplatform.polls.entity;

import lombok.Data;
import org.exoplatform.commons.api.persistence.ExoEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "PollEntity")
@ExoEntity
@Table(name = "ADDONS_POLL_POLL")
@Data


public class PollEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_ADDONS_POLL_POLL_ID", sequenceName = "SEQ_ADDONS_POLL_POLL_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ADDONS_POLL_POLL_ID")
    @Column(name = "ID")
    protected Long id;

    @Column(name = "NAME")
    protected String name;
    @Column(name = "DESCRIPTION")
    protected String description;
    @Column(name = "CREATED_DATE")
    protected Date createdDate;
    @Column(name = "PUBLICATION_DATE")
    protected Date publicationDate;
    @Column(name = "EXPIRATION_DATE")
    protected Date expirationDate;
    @Column(name = "ACTIVE")
    protected Boolean active;
    @Column(name = "USER_NAME")
    protected String userName;

    public PollEntity() {
    }

    public PollEntity(String name, String userName, String description, Date createdDate, Date publicationDate, Date expirationDate, Boolean active ) {
        this.name = name;
        this.userName = userName;
        this.description = description;
        this.createdDate = createdDate;
        this.publicationDate = publicationDate;
        this.expirationDate = expirationDate;
        this.active = active;
    }

}
