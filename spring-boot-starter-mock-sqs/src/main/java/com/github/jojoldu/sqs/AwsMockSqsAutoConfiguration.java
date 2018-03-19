package com.github.jojoldu.sqs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.github.jojoldu.sqs.annotation.ConditionalOnMockSqs;
import com.github.jojoldu.sqs.config.SqsProperties;
import com.github.jojoldu.sqs.config.SqsQueues;
import org.elasticmq.rest.sqs.SQSRestServer;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Configuration
@EnableConfigurationProperties(value = {SqsProperties.class})
@ConditionalOnMockSqs
public class AwsMockSqsAutoConfiguration {
    private SqsProperties sqsProperties;
    private SqsQueues sqsQueues;

    public AwsMockSqsAutoConfiguration(SqsProperties sqsProperties, SqsQueues sqsQueues) {
        this.sqsProperties = sqsProperties;
        this.sqsQueues = sqsQueues;
    }

    @Bean
    @Primary
    @DependsOn("sqsRestServer")
    public AmazonSQSAsync amazonSqs() {
        AmazonSQSAsync sqsAsync = createMockSqsAsync();
        sqsQueues.getQueues().values()
                .forEach(queue -> sqsAsync.createQueue(queue.createQueueRequest()));
        return sqsAsync;
    }

    private AmazonSQSAsync createMockSqsAsync() {
        AmazonSQSAsyncClientBuilder sqsBuilder = AmazonSQSAsyncClientBuilder.standard();
        sqsBuilder.setCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("x", "x")));
        sqsBuilder.setEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsProperties.getEndPoint(), ""));
        return sqsBuilder.build();
    }

    @Bean
    public SQSRestServer sqsRestServer() {
        return SQSRestServerBuilder
                .withInterface(sqsProperties.getHost())
                .withPort(sqsProperties.getPort())
                .start();
    }
}
