package nl.tudelft.ewi.github.jaxrs;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import lombok.extern.slf4j.Slf4j;
import nl.tudelft.ewi.github.GithubHook;
import nl.tudelft.ewi.github.jaxrs.models.Payload;
import nl.tudelft.ewi.github.jaxrs.models.PullRequestEvent;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

@Slf4j
@Path("/hook")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HookResource {

	private final ObjectMapper objectMapper;
	private final GithubHook hook;
	
	@Inject
	public HookResource(ObjectMapper objectMapper, GithubHook hook) {
		this.objectMapper = objectMapper;
		this.hook = hook;
	}
	
	@POST
	@Path("/")
	public Response post(@HeaderParam("HTTP_X_GITHUB_EVENT") String event,
			@Context HttpServletRequest request) throws IOException {
		
		Preconditions.checkNotNull(request);
		
		try {
			Preconditions.checkNotNull(event);
		}
		catch (NullPointerException e) {
			throw new BadRequestException(e);
		}
		
		try(InputStream in = request.getInputStream()) {
			Payload.EventType type = Payload.EventType.valueOf(event.toUpperCase());
			
			switch(type) {
				case PULL_REQUEST:
					hook.onPullRequest(objectMapper.readValue(in, PullRequestEvent.class));
					break;
				default:
					log.info("Unimplemented hook " + type);
					break;
			}
			
			return Response.ok().build();
		}
		catch (JsonParseException | JsonMappingException e) {
			log.warn(e.getMessage(), e);
			throw new InternalServerErrorException(e.getMessage(), Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage() + "\n").build(), e);
		}
		catch (IllegalArgumentException e) {
			throw new BadRequestException(e);
		}
		catch (Exception e) {
			throw new InternalServerErrorException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build(), e);
		}
	}
	
}
