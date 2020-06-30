package examples.consumer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import examples.domain.ExamplePayload;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableBinding(Sink.class)
public class ExampleConsumer {

	private static final ObjectMapper om = new ObjectMapper();
	
	/*
	@StreamListener(Sink.CHANNEL)
	public void receive(ExamplePayload ep) {
		log.info("******************\nAt the Sink\n******************");
		log.info("Received transformed message " + ep.toString() + " of type " + ep.getClass());
	}
	*/
	
	@StreamListener(Sink.CHANNEL)
	public void receive(Message<String> message) throws Exception {
		log.info("******************\nAt the Sink\n******************");
		
		ExamplePayload ep = om.readValue(message.getPayload(), ExamplePayload.class);
		log.info("Received transformed message " + ep.toString() + " of type " + ep.getClass());

		Acknowledgment ack = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		if(ack != null) {
			ack.acknowledge();
			log.info("manual commit complete!");
		}
	}
}
