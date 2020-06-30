package examples.domain;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class ExamplePayloadDeserializer extends StdDeserializer<ExamplePayload> {

	private static final long serialVersionUID = 8645803531234280436L;

	public ExamplePayloadDeserializer() {
		this(null);
	}
	
	protected ExamplePayloadDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public ExamplePayload deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		ExamplePayload ep = new ExamplePayload();
		ObjectCodec codec = parser.getCodec();
		JsonNode node;
		
		node = codec.readTree(parser);
		
		ep.setSeq(node.get("seq").asInt());
		ep.setTimestamp(node.get("timestamp").asLong());
		ep.setRandomString(node.get("randomString").asText());
		
		return ep;
	}
}
