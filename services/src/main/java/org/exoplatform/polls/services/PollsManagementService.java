package org.exoplatform.polls.services;


import java.util.ArrayList;
import java.util.List;

import org.exoplatform.polls.dao.PollDAO;
import org.exoplatform.polls.dto.PollDTO;
import org.exoplatform.polls.entity.PollEntity;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

public class PollsManagementService {

    private final Log LOG = ExoLogger.getLogger(PollsManagementService.class);

    private PollDAO pollDAO;

    public PollsManagementService(PollDAO pollDAO) {
        this.pollDAO = pollDAO;
    }

    public List<PollDTO> getPolls() {
        List<PollEntity> pollEntities = pollDAO.findAll();
        if (pollEntities != null) {
            return toPollDTOList(pollEntities);
        }
        return  new ArrayList<>();

    }

    public PollDTO addPolls(PollDTO pollDTO) {
        return toPollDTO(pollDAO.create(toPollEntity(pollDTO)));
    }

    public void deletePoll(PollEntity pollEntityEntity) {
        pollDAO.delete(pollEntityEntity);
    }

    public PollDTO updatePoll(PollDTO pollDTO) {
        PollEntity newRessource = pollDAO.update(toPollEntity(pollDTO));
        return toPollDTO(newRessource);

    }

    public PollEntity getPollById(Long id) {
        return pollDAO.find(id);

    }


    public List<PollDTO> toPollDTOList(List<PollEntity> pollEntities) {
        List<PollDTO> pollDTOS = new ArrayList<>();
        for (PollEntity PollEntity : pollEntities) {
            pollDTOS.add(toPollDTO(PollEntity));
        }
        return pollDTOS;
    }

    public PollDTO toPollDTO(PollEntity pollEntity) {
        PollDTO pollDTO = new PollDTO();
        pollDTO.setId(pollEntity.getId());
        pollDTO.setName(pollEntity.getName());
        pollDTO.setActive(pollEntity.getActive());
        pollDTO.setCreatedDate(pollEntity.getCreatedDate());
        pollDTO.setExpirationDate(pollEntity.getExpirationDate());
        pollDTO.setPublicationDate(pollEntity.getPublicationDate());
        pollDTO.setUserName(pollEntity.getUserName());
        pollDTO.setDescription(pollEntity.getDescription());
        return pollDTO;
    }

    public PollEntity toPollEntity(PollDTO pollDTO) {
        PollEntity pollEntity = new PollEntity();
        pollEntity.setId(pollDTO.getId());
        pollEntity.setName(pollDTO.getName());
        pollEntity.setActive(pollDTO.getActive());
        pollEntity.setCreatedDate(pollDTO.getCreatedDate());
        pollEntity.setExpirationDate(pollDTO.getExpirationDate());
        pollEntity.setPublicationDate(pollDTO.getPublicationDate());
        pollEntity.setUserName(pollDTO.getUserName());
        pollEntity.setDescription(pollDTO.getDescription());
        return pollEntity;
    }

}
