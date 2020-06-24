package examples.consumer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import examples.domain.ExamplePayload;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableBinding(Sink.class)
public class ExampleConsumer {

	@StreamListener(Sink.CHANNEL)
	public void receive(ExamplePayload ep) {
		log.info("******************\nAt the Sink\n******************");
		log.info("Received transformed message " + ep.toString() + " of type " + ep.getClass());
	}

}
