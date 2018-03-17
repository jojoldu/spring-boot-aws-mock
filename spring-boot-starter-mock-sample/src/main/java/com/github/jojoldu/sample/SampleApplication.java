package com.github.jojoldu.sample;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleApplication.class, args);
	}

	@Bean
	public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqs) {
		return new QueueMessagingTemplate(amazonSqs);
	}

	@Bean
	public ObjectMapper objectMapper(){
		return new ObjectMapper();
	}
}
