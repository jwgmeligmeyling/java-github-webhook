package nl.tudelft.ewi.github.jaxrs.models;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class PullRequest {
	
	private String url;
	private Integer id;
	@JsonProperty("html_url") private String htmlUrl;
	@JsonProperty("diff_url") private String diffUrl;
	@JsonProperty("patch_url") private String patchUrl;
	@JsonProperty("issue_url") private String issueUrl;
	private Integer number;
	private State state;
	private boolean locked;
	private String title;
	private String body;
	private User user;
	private Head head;
	private Head base;
	private boolean merged;
	
	static enum State {
		OPEN, CLOSED;
		
		@JsonCreator
		public static State forValue(String value) {
		    return State.valueOf(value.toUpperCase());
		}
	}
	
}