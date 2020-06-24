package examples.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExamplePayload {

	private int seq;
	private long timestamp;
	private String randomString;
	
}
