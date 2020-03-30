package ch.ubique.starsdk.data.output;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Base output handler for implementation that generate JSON output.
 *
 * @author alig
 */
public abstract class JsonOutputHandler implements OutputHandler {

	protected ObjectMapper objectMapper;
	private JsonFactory factory;

	public JsonOutputHandler() {
		this.factory = new JsonFactory();
		this.objectMapper = new ObjectMapper(factory);
		this.objectMapper.registerModule(new JavaTimeModule());
		this.objectMapper.registerModule(new JodaModule());
	}
}
