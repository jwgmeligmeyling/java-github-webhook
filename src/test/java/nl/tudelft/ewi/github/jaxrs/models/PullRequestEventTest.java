package nl.tudelft.ewi.github.jaxrs.models;

import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
public class PullRequestEventTest {

	private ObjectMapper mapper;
	
	@Before
	public void setupObjectMapper() {
		mapper = new ObjectMapper();
//		mapper.registerModule(new MappingModule());
	}
	
	@Test
	public void test() throws JsonProcessingException, IOException {
		try(InputStream in = PullRequestEventTest.class.getResourceAsStream("/pull-request.example.json")) {
			PullRequestEvent payload = mapper.readValue(in, PullRequestEvent.class);
			log.info("Payload was {} ", payload);
		}
	}
	
}
