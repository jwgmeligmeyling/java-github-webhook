package nl.tudelft.ewi.github.jaxrs.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class PullRequestEvent extends Payload {

	public PullRequestEvent() {
		setEventType(EventType.PULL_REQUEST);
	}
	
	private Action action;
	
	private Integer number;

	@JsonProperty("pull_request")
	private PullRequest pullRequest;
	
	private Repository repository;
	
	private User sender;
	
	public static enum Action {
		ASSGIGNED, UNASSIGNED, LABELED, UNLABELED, OPENED, CLOSED, REOPENED, SYNCHRONIZE;
		
		@JsonCreator
		public static Action forValue(String value) {
		    return Action.valueOf(value.toUpperCase());
		}
	}
	
}
