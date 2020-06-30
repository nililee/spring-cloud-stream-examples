package examples.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonDeserialize(using = ExamplePayloadDeserializer.class)
public class ExamplePayload {

	private int seq;
	private long timestamp;
	private String randomString;
	
}
