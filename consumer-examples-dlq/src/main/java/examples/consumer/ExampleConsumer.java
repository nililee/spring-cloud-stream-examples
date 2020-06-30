package examples.consumer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import examples.domain.ExamplePayload;
import examples.exception.SomeBusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableBinding(Sink.class)
public class ExampleConsumer {

	private final static ObjectMapper om = new ObjectMapper();
	
	@StreamListener(Sink.CHANNEL)
	public void receive(Message<String> message) throws Exception {
		log.info("******************\nAt the Sink\n******************");
		
		ExamplePayload ep = om.readValue(message.getPayload(), ExamplePayload.class);
		log.info("Received transformed message " + ep.toString() + " of type " + ep.getClass());
		
		if(ep.getTimestamp() % 3 == 0) {
			throw new SomeBusinessException("Intentionally raised!");
		}

		Acknowledgment ack = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		if(ack != null) {
			ack.acknowledge();
			log.info("manual commit complete!");
		}
	}
}
