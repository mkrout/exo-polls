package org.exoplatform.polls.dao;

import java.util.ArrayList;
import java.util.List;

import org.exoplatform.commons.persistence.impl.GenericDAOJPAImpl;
import org.exoplatform.polls.entity.PollResponseEntity;
import org.exoplatform.polls.entity.UserResponseEntity;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UserResponseDAO extends GenericDAOJPAImpl<UserResponseEntity, Long> {

    private static final Log LOG = ExoLogger.getLogger(UserResponseDAO.class);

    public List<UserResponseEntity> getUserResponsesByPoll(Long id) {

        TypedQuery<UserResponseEntity> query = getEntityManager().createNamedQuery("UserResponseEntity.getResponsesByPoll", UserResponseEntity.class)
                .setParameter("id", id);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<UserResponseEntity>();
        } catch (Exception e) {
            LOG.error("Error occurred when trying to get list of responses by poll {}", id, e);
            return new ArrayList<UserResponseEntity>();
        }
    }

    public Long countResponsesByUserAndPoll(Long id, String userName) {

       return getEntityManager().createNamedQuery("UserResponseEntity.countResponsesByUserAndPoll", Long.class)
                .setParameter("id", id).setParameter("userName", userName).getSingleResult();

    }
    public Long countResponseByPollAndQuestionId(Long idPoll, Long idQuestion, Long idResponse) {
        TypedQuery<Long> query = getEntityManager().createNamedQuery("UserResponseEntity.countResponsesByPollAndQuestion", Long.class);
        query.setParameter("idPoll",idPoll);
        query.setParameter("idQuestion",idQuestion);
        query.setParameter("idResponse",idResponse);
        Long count = query.getSingleResult();
        return count == null ? 0 : count;

    }

    public Long countResponsesByQuestionAndResponse(Long question, Long response) {

        return getEntityManager().createNamedQuery("UserResponseEntity.countResponsesByQuestionAndResponse", Long.class)
                .setParameter("question", question).setParameter("response", response).getSingleResult();

    }

}