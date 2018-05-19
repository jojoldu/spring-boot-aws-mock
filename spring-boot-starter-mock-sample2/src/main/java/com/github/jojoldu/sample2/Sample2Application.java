package com.github.jojoldu.sample2;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Sample2Application {

	@Bean
	public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqs) {
		return new QueueMessagingTemplate(amazonSqs);
	}

	public static void main(String[] args) {
		SpringApplication.run(Sample2Application.class, args);
	}
}
