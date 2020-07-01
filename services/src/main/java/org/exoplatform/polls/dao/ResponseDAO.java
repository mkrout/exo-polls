package org.exoplatform.polls.dao;

import java.util.ArrayList;
import java.util.List;

import org.exoplatform.commons.persistence.impl.GenericDAOJPAImpl;
import org.exoplatform.polls.entity.PollResponseEntity;
import org.exoplatform.polls.entity.QuestionEntity;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ResponseDAO extends GenericDAOJPAImpl<PollResponseEntity, Long> {

    private static final Log LOG = ExoLogger.getLogger(ResponseDAO.class);

    public List<PollResponseEntity> getResponsesByQuestion(Long id) {

        TypedQuery<PollResponseEntity> query = getEntityManager().createNamedQuery("PollResponseEntity.getResponsesByQuestion", PollResponseEntity.class)
                .setParameter("id", id);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<PollResponseEntity>();
        } catch (Exception e) {
            LOG.error("Error occurred when trying to get list of responses by question {}", id, e);
            return new ArrayList<PollResponseEntity>();
        }
    }
}