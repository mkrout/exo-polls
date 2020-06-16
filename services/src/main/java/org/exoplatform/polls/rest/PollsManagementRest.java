package org.exoplatform.polls.rest;

import static org.exoplatform.polls.Utils.*;

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

import org.exoplatform.polls.dto.PollDTO;
import org.exoplatform.polls.dto.QuestionDTO;
import org.exoplatform.polls.dto.ResponseDTO;
import org.exoplatform.polls.entity.PollEntity;
import org.exoplatform.polls.entity.QuestionEntity;
import org.exoplatform.polls.entity.ResponseEntity;
import org.exoplatform.polls.services.PollsManagementService;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;


import io.swagger.jaxrs.PATCH;
import org.exoplatform.services.rest.resource.ResourceContainer;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.service.rest.Util;

@Path("/pollsmanagement")
@Produces(MediaType.APPLICATION_JSON)

public class PollsManagementRest implements ResourceContainer {

  private final Log                  LOG                 = ExoLogger.getLogger(PollsManagementRest.class);

  private final String               portalContainerName = "portal";

  private PollsManagementService pollsManagementService;

  public PollsManagementRest(PollsManagementService pollsManagementService) {
   this.pollsManagementService=pollsManagementService;
  }

  @GET
  @Path("polls")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPolls(@Context UriInfo uriInfo) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      return Response.ok(pollsManagementService.getPolls()).build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to get polls list", e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }
  //add_poll
  @POST
  @Path("polls")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response add(@Context UriInfo uriInfo, PollDTO pollDTO) throws Exception {
    try {
      pollsManagementService.addPolls(pollDTO);
      return Response.ok("Poll added").build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to add new poll {}", pollDTO.getName(), e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }
  //add_question
  @POST
  @Path("questions")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response add(@Context UriInfo uriInfo, QuestionDTO questionDTO) throws Exception{
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
  public Response add(@Context UriInfo uriInfo,   ResponseDTO responseDTO) throws Exception {
    try {
      pollsManagementService.addResponse(responseDTO);
      return Response.ok("Response added").build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to add new response {}", responseDTO.getResponse(), e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DELETE
  @Path("polls/{id}")
  public Response delete(@Context UriInfo uriInfo, @PathParam("id") Long id) throws Exception {
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
  @DELETE
  @Path("questions/{id}")
  public Response delete(@Context UriInfo uriInfo, @PathParam("id") Long id) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      QuestionEntity questionEntity = pollsManagementService.getQuestionById(id);
      if (questionEntity == null) {
        return Response.status(Response.Status.NOT_FOUND).entity("Question not found").build();
      }
      pollsManagementService.deleteQuestion(questionEntity);
      LOG.info("Question {} deleted by {}", id, sourceIdentity.getRemoteId());
      return Response.ok("question deleted").build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to delete Poll {}", id, e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }
  @DELETE
  @Path("responses/{id}")
  public Response delete(@Context UriInfo uriInfo, @PathParam("id") Long id) throws Exception {
    Identity sourceIdentity = Util.getAuthenticatedUserIdentity(portalContainerName);
    if (sourceIdentity == null) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    try {
      ResponseEntity responseEntity = pollsManagementService.getResponseById(id);
      if (responseEntity == null) {
        return Response.status(Response.Status.NOT_FOUND).entity("response not found").build();
      }
      pollsManagementService.deleteResponse(responseEntity);
      LOG.info("Response {} deleted by {}", id, sourceIdentity.getRemoteId());
      return Response.ok("response deleted").build();
    } catch (Exception e) {
      LOG.error("An error occured when trying to delete Response {}", id, e);
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }

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
