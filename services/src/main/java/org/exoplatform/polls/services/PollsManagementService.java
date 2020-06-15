package org.exoplatform.polls.services;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.exoplatform.polls.dao.PollDAO;
import org.exoplatform.polls.dto.PollDTO;
import org.exoplatform.polls.dto.QuestionDTO;
import org.exoplatform.polls.dto.ResponseDTO;
import org.exoplatform.polls.entity.PollEntity;
import org.exoplatform.polls.entity.QuestionEntity;
import org.exoplatform.polls.entity.ResponseEntity;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

public class PollsManagementService {

    private final Log LOG = ExoLogger.getLogger(PollsManagementService.class);

    private PollDAO pollDAO;
    private QuestionDTO questionDTO;
    private ResponseDTO responseDTO;
    private ResponseEntity responseEntity;
    private SimpleDateFormat formatter  = new SimpleDateFormat("mm/dd/yyyy");

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
   //        public static final String TASK_DATE_FORMAT= "yyyy-MM-dd";
    //        public static final createdDateFormatted createdDate=new createdDateFormatted(TASK_DATE_FORMAT);
    //       public static final createdDate.parse("yyyy-MM-dd");




    public PollDTO addPolls(PollDTO pollDTO) {
        try {
            pollDTO.setCreatedDate(formatter.parse(pollDTO.getCreatedDateFormatted()));
            pollDTO.setPublicationDate(formatter.parse(pollDTO.getPublicationDateFormatted()));
            pollDTO.setExpirationDate(formatter.parse(pollDTO.getExpirationDateFormatted()));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return toPollDTO(pollDAO.create(toPollEntity(pollDTO)));


    }
    public void addQuestion(QuestionDTO questionDTO) {

        return toQuestionDTO(questionDAO.create(toQuestionEntity(questionDTO)));
    }
    public void addResponse (ResponseDTO responseDTO) {
        return toResponseDTO(responseDAO.create(toResponseEntity(responseDTO)));
    }


    //poll
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
    //question
    public void deleteQuestion(QuestionEntity questionEntityEntity) {
        questionDAO.delete(questionEntityEntity);
    }

    public QuestionDTO updateQuestion(QuestionDTO questionDTO) {
        QuestionEntity newRessource = questionDAO.update(toQuestionEntity(questionDTO));
        return toQuestionDTO(newRessource);

    }
    public QuestionEntity getQuestionById(Long id) {
        return questionDAO.find(id);

    }
    //response
    public void deleteResponse(ResponseEntity responseEntityEntity) {
        responseDAO.delete(responseEntityEntity);
    }

    public ResponseDTO updateResponse(ResponseDTO responseDTO) {
        ResponseEntity newRessource = responseDAO.update(toResponseEntity(responseDTO));
        return toResponseDTO(newRessource);

    }
    public ResponseEntity getResponseById(Long id) {
        return responseDAO.find(id);

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
        if(pollEntity.getCreatedDate()!=null)pollDTO.setCreatedDateFormatted(formatter.format(pollEntity.getCreatedDate()));
        if(pollEntity.getExpirationDate()!=null)pollDTO.setExpirationDateFormatted(formatter.format(pollEntity.getExpirationDate()));
        if(pollEntity.getPublicationDate()!=null)pollDTO.setPublicationDateFormatted(formatter.format(pollEntity.getPublicationDate()));
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

    public QuestionDTO toQuestioDTO(QuestionEntity questionEntity) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(questionEntity.getId());
        questionDTO.setQuestion(questionEntity.getQuestion());
        questionDTO.setPoll(toPollDTO(questionEntity.getPollEntity()));
        return questionDTO;
    }

    public QuestionEntity toQuestionEntity(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(questionDTO.getId());
        questionEntity.setQuestion(questionDTO.getQuestion());
        PollEntity pollEntity=this.pollfromlongID(questionDTO.getId());
        questionEntity.setPollEntity(pollEntity);
        return questionEntity;
    }
    public PollEntity pollfromlongID (Long id){
        PollEntity poll=new PollEntity();
        poll.setId(id);
        return poll ;
    }

    public ResponseDTO toResponseDTO(ResponseEntity responseEntity) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setId( responseEntity.getId());
        responseDTO.setResponse( responseEntity.getResponse());
        responseDTO.setQuestion(toQuestioDTO(responseEntity.getQuestionEntity()));
        return responseDTO;
    }

    public ResponseEntity toQResponseEntity(ResponseDTO responseDTO) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setId(responseDTO.getId());
        responseEntity.setResponse(responseDTO.getResponse());
        QuestionEntity questionEntity=this.questionfromlongID(responseDTO.getId());
        responseEntity.setQuestionEntity(questionEntity);
        return responseEntity;
    }
    public QuestionEntity questionfromlongID (Long id){
        QuestionEntity question=new QuestionEntity();
        question.setId(id);
        return question ;
    }



}
