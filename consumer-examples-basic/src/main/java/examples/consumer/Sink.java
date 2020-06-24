package examples.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Sink {

	String CHANNEL = "sink-channel";

	@Input(Sink.CHANNEL)
	SubscribableChannel input();
	
}
