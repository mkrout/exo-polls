package org.exoplatform.polls.services;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.exoplatform.polls.dao.PollDAO;
import org.exoplatform.polls.dao.QuestionDAO;
import org.exoplatform.polls.dao.ResponseDAO;
import org.exoplatform.polls.dao.UserResponseDAO;
import org.exoplatform.polls.dto.*;
import org.exoplatform.polls.entity.PollEntity;
import org.exoplatform.polls.entity.QuestionEntity;
import org.exoplatform.polls.entity.PollResponseEntity;
import org.exoplatform.polls.entity.UserResponseEntity;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

public class PollsManagementService {

    private final Log LOG = ExoLogger.getLogger(PollsManagementService.class);

    private PollDAO pollDAO;
    private QuestionDAO questionDAO;
    private ResponseDAO responseDAO;
    private UserResponseDAO userResponseDAO;
    private SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-mm-dd");

    public PollsManagementService(PollDAO pollDAO, QuestionDAO questionDAO, ResponseDAO responseDAO, UserResponseDAO userResponseDAO) {
        this.pollDAO = pollDAO;
        this.questionDAO = questionDAO;
        this.responseDAO = responseDAO;
        this.userResponseDAO = userResponseDAO;
    }

    public List<PollDTO> getPolls(String userName) {
        List<PollEntity> pollEntities = pollDAO.findAll();
        if (pollEntities != null) {
            List<PollDTO> pollDTOS = new ArrayList<>();
            for (PollEntity PollEntity : pollEntities) {
                PollDTO poll = toPollDTO(PollEntity);
                poll.setResponded(false);
                if(userResponseDAO.countResponsesByUserAndPoll(poll.getId(),userName)>0){
                    poll.setResponded(true);
                }
                pollDTOS.add(poll);
            }
            return pollDTOS;
        }
        return  new ArrayList<>();

    }

    public Long getCountResponseByPollAndQuestion(Long idPoll, Long idQuestion, Long idResponse) {
        return userResponseDAO.countResponseByPollAndQuestionId(idPoll,idQuestion,idResponse);
    }

    public void addUserResponse(PollDetails pollDetails, String userName) {
        try {

            for (Question_Resoponses question : pollDetails.getQuestions()){
                if(question.getResponse()!=null){
                    UserResponseEntity userResponseEntity =  new UserResponseEntity(userName,toQuestionEntity(question.getQuestion()),  toResponseEntity(question.getResponse()), toPollEntity(pollDetails.getPoll()));
                    userResponseDAO.create(userResponseEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<UserResponseDTO> getUserResponsesByPoll(Long id) {
        List<UserResponseEntity> responseEntities = userResponseDAO.getUserResponsesByPoll(id);
        return responseEntities.stream().map(this::toUserResponseDto).collect(Collectors.toList());
    }

    public PollDTO addPolls(PollDetails pollDetails, String userName) {
        PollEntity pollEntity = null;
        try {
            pollEntity = new PollEntity(pollDetails.getPoll().getName(), userName, pollDetails.getPoll().getDescription(), formatter.parse(pollDetails.getPoll().getCreatedDateFormatted()), formatter.parse(pollDetails.getPoll().getPublicationDateFormatted()), formatter.parse(pollDetails.getPoll().getExpirationDateFormatted()), true );
            pollEntity = pollDAO.create(pollEntity);
            for (Question_Resoponses question : pollDetails.getQuestions()){
                QuestionEntity questionEntity = toQuestionEntity(question.getQuestion());
                questionEntity.setPollEntity(pollEntity);
                questionEntity = questionDAO.create(questionEntity);
                for(ResponseDTO responseDTO : question.getResponses()){
                    PollResponseEntity pollResponseEntity = toResponseEntity(responseDTO);
                    pollResponseEntity.setQuestionEntity(questionEntity);
                    responseDAO.create(pollResponseEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toPollDTO(pollEntity);


    }

    //question_add
    public QuestionDTO addQuestion(QuestionDTO questionDTO) {

        return toQuestioDTO(questionDAO.create(toQuestionEntity(questionDTO)));
    }
    //response_add
    public ResponseDTO addResponse (ResponseDTO responseDTO) {
        return toResponseDTO(responseDAO.create(toResponseEntity(responseDTO)));
    }


    //poll_delete_and_update
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
    //question_delete_and_update
    public void deleteQuestion(QuestionEntity questionEntityEntity) {
        questionDAO.delete(questionEntityEntity);
    }

    public QuestionDTO updateQuestion(QuestionDTO questionDTO) {
        QuestionEntity newRessource = questionDAO.update(toQuestionEntity(questionDTO));
        return toQuestioDTO(newRessource);

    }
    public QuestionEntity getQuestionById(Long id) {
        return questionDAO.find(id);

    }

    public List<QuestionEntity> getQuestionsByPoll(long id) {
        return questionDAO.getQuestionsByPoll(id);

    }

    //response_delete_and_update
    public void deleteResponse(PollResponseEntity pollResponseEntityEntity) {
        responseDAO.delete(pollResponseEntityEntity);
    }

    public ResponseDTO updateResponse(ResponseDTO responseDTO) {
        PollResponseEntity newRessource = responseDAO.update(toResponseEntity(responseDTO));
        return toResponseDTO(newRessource);

    }
    public PollResponseEntity getResponseById(Long id) {
        return responseDAO.find(id);

    }

    public List<ResponseDTO> getResponsesByQuestion(long id) {
        List<PollResponseEntity> responseEntities = responseDAO.getResponsesByQuestion(id);
        return responseEntities.stream().map(this::toResponseDTO).collect(Collectors.toList());

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

    public ResponseDTO toResponseDTO(PollResponseEntity pollResponseEntity) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setId( pollResponseEntity.getId());
        responseDTO.setResponse( pollResponseEntity.getResponse());
        responseDTO.setQuestion(toQuestioDTO(pollResponseEntity.getQuestionEntity()));
        return responseDTO;
    }

    public PollResponseEntity toResponseEntity(ResponseDTO responseDTO) {
        PollResponseEntity pollResponseEntity = new PollResponseEntity();
        pollResponseEntity.setId(responseDTO.getId());
        pollResponseEntity.setResponse(responseDTO.getResponse());
        QuestionEntity questionEntity=this.questionfromlongID(responseDTO.getId());
        pollResponseEntity.setQuestionEntity(questionEntity);
        return pollResponseEntity;
    }
    public QuestionEntity questionfromlongID (Long id){
        QuestionEntity question=new QuestionEntity();
        question.setId(id);
        return question ;
    }


    public UserResponseDTO toUserResponseDto(UserResponseEntity userResponseEntity) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(userResponseEntity.getId());
        responseDTO.setPollResponseDto(toResponseDTO(userResponseEntity.getPollResponseEntity()));
        responseDTO.setQuestionDto(toQuestioDTO(userResponseEntity.getQuestionEntity()));
        responseDTO.setUserName(userResponseEntity.getUserName());
        responseDTO.setPollDTO(toPollDTO(userResponseEntity.getPollEntity()));

        return responseDTO;
    }


/*
    public List<? extends Serializable> getQuestions() {

            List<QuestionEntity> questionEntities= questionDAO.findAll();
            if (questionEntities != null) {
                return toQuestionDTOList(questionEntities);

            }

        return new ArrayList<>();
    }

    private List<ResponseDTO> toQuestionDTOList(List<QuestionEntity> responses) {
        List<ResponseDTO> questionDTOS = new ArrayList<>();

        for (ResponseEntity QuestionEntity : questionEntities) {
        questionDTOS.add(toResponseDTO(QuestionEntity));
        }
        return questionDTOS;
    }


    public List<ResponseDTO> getResponses() {

        List<QuestionEntity> responseEntities= questionDAO.findAll();
        if (questionEntities != null) {
            return toQuestionDTOList(questionEntities);

        }

        return new ArrayList<>();
    }

    private List<ResponseDTO> toResponseDTOList(List<ResponseEntity> responses) {
        List<ResponseDTO> responseDTOS = new ArrayList<>();

        for (ResponseEntity PollEntity : responseEntities) {
          responseDTOS.add(toResponseDTO(ResponseEntity));
        }
        return responseDTOS;
    }*/
}
