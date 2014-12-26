package nl.tudelft.ewi.github.jaxrs.models;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Head {

	private String label;
	private String ref;
	private String sha;
	private User user;
	private Repository repo;
}
