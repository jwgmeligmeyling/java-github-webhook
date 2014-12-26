package nl.tudelft.ewi.github.jaxrs.models;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Repository {

	private Integer id;
	private String name;
	@JsonProperty("full_name") private String fullName;
	private User owner;
	@JsonProperty("private") private boolean privateRepository;
	@JsonProperty("html_url") private String htmlUrl;
	@JsonProperty("clone_url") private String cloneUrl;
	private String description;
	private boolean fork;
	
}
