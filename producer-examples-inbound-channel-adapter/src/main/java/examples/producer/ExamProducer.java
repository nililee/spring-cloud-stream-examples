package examples.producer;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import examples.domain.ExamplePayload;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableBinding(Source.class)
public class ExamProducer {
	
	private final static int RANDOM_STRING_LENGTH = 10;
	
	private final static ObjectMapper om = new ObjectMapper();
	private static AtomicInteger idx = new AtomicInteger();

	@Bean
	@InboundChannelAdapter(value = Source.CHANNEL, poller = @Poller(fixedDelay = "100", maxMessagesPerPoll = "1"))
	public MessageSource<String> timerMessageSource() {
		
		return new MessageSource<String>() {
			public Message<String> receive() {
				
				log.info("******************\nAt the Source\n******************");
				
				Message<String> message = null;
				ExamplePayload ep = new ExamplePayload(
					idx.incrementAndGet(),
					System.currentTimeMillis(),
					RandomStringUtils.random(RANDOM_STRING_LENGTH, true, false)
				);
				
				try {
					message = MessageBuilder.withPayload(om.writeValueAsString(ep))
						.setHeader(KafkaHeaders.MESSAGE_KEY, String.valueOf(ep.getSeq()))
						.build();
					log.info(message.getHeaders() + " / " + message.getPayload());
					
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				
				return message;
			}
		};
	}
}
