package nl.tudelft.ewi.github.jaxrs.models;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class User {

	private String login;
	private Integer id;
	
}
