package org.exoplatform.polls.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.core.UriInfo;

import org.exoplatform.polls.dto.*;
import org.exoplatform.polls.entity.PollEntity;
import org.exoplatform.polls.entity.QuestionEntity;
import org.exoplatform.polls.entity.PollResponseEntity;
import org.exoplatform.polls.services.PollsManagementService;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;


import org.exoplatform.services.rest.resource.ResourceContainer;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.service.rest.Util;
import org.json.JSONArray;
import org.json.JSONObject;

@Path("/pollsmanagement")
@Produces(MediaType.APPLICATION_JSON)

public class PollsManagementRest implements ResourceContainer {

  private final Log                  LOG                 = ExoLogger.getLogger(PollsManagementRest.class);

  private final String               portalContainerName = "portal";

  private PollsManagementService pollsManagementService;


  public PollsManagementRest(PollsManagementService pollsManagementService) {
   this.pollsManagementService=pollsManagementService;
  }



//get polls
  @GET
  @Path("polls")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPolls(@Context UriInfo uriInfo) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      return Response.ok(pollsManagementService.getPolls(sourceIdentity.getRemoteId())).build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to get polls list", e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

//get polls
  @GET
  @Path("polls/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPollById(@Context UriInfo uriInfo, @PathParam("id") Long id) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {

      PollEntity pollEntity = pollsManagementService.getPollById(id);
      if(pollEntity ==null ){
        return  Response.status(Response.Status.NOT_FOUND).build();
      }
      PollDetails pollDetails = new PollDetails();
      pollDetails.setPoll(pollsManagementService.toPollDTO(pollEntity));
      List<QuestionEntity> questionEntities = pollsManagementService.getQuestionsByPoll(pollEntity.getId());
      List<Question_Resoponses> question_Resoponses= new ArrayList<Question_Resoponses>();
      for(QuestionEntity questionEntity : questionEntities){
        Question_Resoponses  question = new Question_Resoponses();
        question.setQuestion(pollsManagementService.toQuestioDTO(questionEntity));
        question.setResponses(pollsManagementService.getResponsesByQuestion(questionEntity.getId()));
        question_Resoponses.add(question);
      }

      pollDetails.setQuestions(question_Resoponses);
      return Response.ok(pollDetails).build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to get polls list", e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GET
  @Path("responses/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getResponsesByPoll(@Context UriInfo uriInfo, @PathParam("id") Long id) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {

      PollEntity pollEntity = pollsManagementService.getPollById(id);
      if(pollEntity ==null ){
        return  Response.status(Response.Status.NOT_FOUND).build();
      }
      PollDetails pollDetails = new PollDetails();
      pollDetails.setPoll(pollsManagementService.toPollDTO(pollEntity));
      List<QuestionEntity> questionEntities = pollsManagementService.getQuestionsByPoll(pollEntity.getId());
      List<Question_Resoponses> question_Resoponses= new ArrayList<Question_Resoponses>();
      for(QuestionEntity questionEntity : questionEntities){
        Question_Resoponses  question = new Question_Resoponses();
        question.setQuestion(pollsManagementService.toQuestioDTO(questionEntity));
        question.setResponses(pollsManagementService.getResponsesByQuestion(questionEntity.getId()));
        question_Resoponses.add(question);
      }

      pollDetails.setQuestions(question_Resoponses);
      return Response.ok(pollDetails).build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to get polls list", e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }


/*


  //Get questions
  @GET
  @Path("questions")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getQuestions(@Context UriInfo uriInfo) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      return Response.ok(pollsManagementService.getQuestions()).build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to get polls list", e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }



  //Get response
  @GET
  @Path("responses")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getResponses(@Context UriInfo uriInfo) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      return Response.ok(pollsManagementService.getResponses()).build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to get polls list", e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }


*/


  //add_poll
  @POST
  @Path("polls")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response add(@Context UriInfo uriInfo, PollDetails poll) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      pollsManagementService.addPolls(poll,sourceIdentity.getRemoteId());
      return Response.ok("Poll added").build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to add new poll {}", poll.getPoll().getName(), e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }


  //add_response
  @POST
  @Path("userresponse")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addUserRespone(@Context UriInfo uriInfo, PollDetails poll) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      pollsManagementService.addUserResponse(poll,sourceIdentity.getRemoteId());
      return Response.ok("Response added").build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to add new poll {}", poll.getPoll().getName(), e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }


  @GET
  @Path("userresponse/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUserRespone(@Context UriInfo uriInfo, @PathParam("id") Long id) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      return Response.ok(pollsManagementService.getUserResponsesByPoll(id)).build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to get responses  for poll{}", id, e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GET
  @Path("responsenumber/{idPoll}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCountRespone(@Context UriInfo uriInfo, @PathParam("idPoll") Long idPoll) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      List<QuestionEntity> questionEntities = pollsManagementService.getQuestionsByPoll(idPoll);
      JSONArray global = new JSONArray();
      for(QuestionEntity questionEntity : questionEntities){
        JSONArray responses = new JSONArray();
        List<ResponseDTO> pollResponses = pollsManagementService.getResponsesByQuestion(questionEntity.getId());
        for(ResponseDTO responseDTO : pollResponses){
          JSONObject response = new JSONObject();
          response.put("responseId",responseDTO.getId());
          response.put("response",responseDTO.getResponse());
          response.put("number",pollsManagementService.countResponsesByQuestionAndResponse(questionEntity.getId(),responseDTO.getId()));
          responses.put(response);
        }
        JSONObject question = new JSONObject();
        question.put("questionId",questionEntity.getId());
        question.put("question",questionEntity.getQuestion());
        question.put("responses",responses);
        global.put(question);
      }

      return Response.ok(global.toString()).build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to get responses  number", e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  //add_question
  @POST
  @Path("questions")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addquestions(@Context UriInfo uriInfo, QuestionDTO questionDTO) throws Exception{
    try {
      pollsManagementService.addQuestion(questionDTO);
      return Response.ok("Question added").build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to add new question {}", questionDTO.getQuestion(), e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }


  //add_response
  @POST
  @Path("responses")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addResponse(@Context UriInfo uriInfo,   ResponseDTO responseDTO) throws Exception {
    try {
      pollsManagementService.addResponse(responseDTO);
      return Response.ok("Response added").build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to add new response {}", responseDTO.getResponse(), e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }



  // delete poll
  @DELETE
  @Path("polls/{id}")
  public Response deletePolls(@Context UriInfo uriInfo, @PathParam("id") Long id) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      PollEntity pollEntity = pollsManagementService.getPollById(id);
      if (pollEntity == null) {
        return Response.status(Response.Status.NOT_FOUND).entity("Poll not found").build();
      }
      pollsManagementService.deletePoll(pollEntity);
      LOG.info("Poll {} deleted by {}", id, sourceIdentity.getRemoteId());
      return Response.ok("Poll deleted").build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to delete Poll {}", id, e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }

  //delete question
  @DELETE
  @Path("questions/{id}")
  public Response deletequestions(@Context UriInfo uriInfo, @PathParam("idquestion") Long idquestion) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      QuestionEntity questionEntity = pollsManagementService.getQuestionById(idquestion);
      if (questionEntity == null) {
        return Response.status(Response.Status.NOT_FOUND).entity("Question not found").build();
      }
      pollsManagementService.deleteQuestion(questionEntity);
      LOG.info("Question {} deleted by {}", idquestion, sourceIdentity.getRemoteId());
      return Response.ok("question deleted").build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to delete Poll {}", idquestion, e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }
  //delete question
  @DELETE
  @Path("responses/{id}")
  public Response deleteresponses(@Context UriInfo uriInfo, @PathParam("idresponse") Long idresponse) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      PollResponseEntity pollResponseEntity = pollsManagementService.getResponseById(idresponse);
      if (pollResponseEntity == null) {
        return Response.status(Response.Status.NOT_FOUND).entity("Response not found").build();
      }
      pollsManagementService.deleteResponse(pollResponseEntity);

      LOG.info("Question {} deleted by {}", idresponse, sourceIdentity.getRemoteId());
      return Response.ok("response deleted").build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to delete Poll {}", idresponse, e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }


  //update poll
  @PUT
  @Path("polls")
  @Consumes(MediaType.APPLICATION_JSON)
  @RolesAllowed("ux-team")
  public Response update(@Context UriInfo uriInfo, PollDTO pollDTO) throws Exception {
    try {
      pollsManagementService.updatePoll(pollDTO);
      return Response.ok("Poll updated").build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to update Poll {}", pollDTO.getId(), e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }


}
