package examples.producer;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import examples.domain.ExamplePayload;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableBinding(Source.class)
@Component
public class ProducerApplicationRunner implements ApplicationRunner {

	private final static int RANDOM_STRING_LENGTH = 10;
	
	private final static ObjectMapper om = new ObjectMapper();
	private static AtomicInteger idx = new AtomicInteger();
	
    @Autowired
    Source exampleSource;
	
	@Override
    public void run(ApplicationArguments args) throws Exception {
		
		for(int i = 0; i < 10; i++) {
			
			ExamplePayload ep = new ExamplePayload(
				idx.incrementAndGet(),
				System.currentTimeMillis(),
				RandomStringUtils.random(RANDOM_STRING_LENGTH, true, false)
			);
			
			log.info("************* " + (i + 1) + "th trying... ");
			
			Message<String> message =
				MessageBuilder.withPayload(om.writeValueAsString(ep))
					.setHeader(KafkaHeaders.MESSAGE_KEY, String.valueOf(ep.getSeq()))
					.build();
			
			boolean isMessageSent = exampleSource.output().send(message);
			log.info(message.getHeaders() + " / " + message.getPayload() + " ---> " + isMessageSent);
		}
    }
}
