package examples.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Source {

	String CHANNEL = "source-channel";

	@Output(Source.CHANNEL)
	MessageChannel output();
	
}
