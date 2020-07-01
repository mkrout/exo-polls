package org.exoplatform.polls.dao;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import org.exoplatform.commons.persistence.impl.GenericDAOJPAImpl;
import org.exoplatform.polls.entity.PollEntity;
import org.exoplatform.polls.entity.QuestionEntity;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

public class QuestionDAO extends GenericDAOJPAImpl<QuestionEntity, Long> {
    private static final Log LOG = ExoLogger.getLogger(QuestionDAO.class);

    public List<QuestionEntity> getQuestionsByPoll(Long id) {

        TypedQuery<QuestionEntity> query = getEntityManager().createNamedQuery("QuestionEntity.getQuestionsByPoll", QuestionEntity.class)
                .setParameter("id", id);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<QuestionEntity>();
        } catch (Exception e) {
            LOG.error("Error occurred when trying to get list of question by pollid {}", id, e);
            return new ArrayList<QuestionEntity>();
        }
    }

}




