package org.exoplatform.polls.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.exoplatform.commons.persistence.impl.GenericDAOJPAImpl;
import org.exoplatform.polls.entity.QuestionEntity;
import org.exoplatform.polls.entity.ResponseEntity;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

public class ResponseDAO extends GenericDAOJPAImpl<ResponseEntity, Long> {

    private static final Log LOG = ExoLogger.getLogger(ResponseDAO.class);
}