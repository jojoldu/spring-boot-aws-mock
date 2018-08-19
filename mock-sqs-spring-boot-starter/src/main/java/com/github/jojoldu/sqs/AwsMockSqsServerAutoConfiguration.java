package com.github.jojoldu.sqs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.github.jojoldu.sqs.annotation.server.ConditionalOnMissingMockSqsServer;
import com.github.jojoldu.sqs.annotation.server.ConditionalOnMockSqs;
import com.github.jojoldu.sqs.annotation.server.ConditionalOnMockSqsServer;
import com.github.jojoldu.sqs.annotation.server.MockServerMessageType;
import com.github.jojoldu.sqs.config.SqsProperties;
import com.github.jojoldu.sqs.config.SqsQueues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticmq.rest.sqs.SQSRestServer;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnMockSqs
public class AwsMockSqsServerAutoConfiguration {
    private final SqsProperties sqsProperties;
    private final SqsQueues sqsQueues;

    @Bean("amazonSqs")
    @Primary
    @ConditionalOnMissingMockSqsServer
    public AmazonSQSAsync useMockAmazonSqs() {
        log.info(MockServerMessageType.USE_SERVER.getMessage());
        return createMockSqsAsync();
    }

    @Bean("amazonSqs")
    @Primary
    @DependsOn("sqsRestServer")
    @ConditionalOnMockSqsServer
    public AmazonSQSAsync createMockAmazonSqs() {
        AmazonSQSAsync sqsAsync = createMockSqsAsync();
        sqsQueues.createQueue(sqsAsync);
        return sqsAsync;
    }

    private AmazonSQSAsync createMockSqsAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("x", "x")))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(sqsProperties.getEndPoint(), ""))
                .build();
    }

    @Bean
    @Primary
    @ConditionalOnMockSqsServer
    public SQSRestServer sqsRestServer() {
        log.info(MockServerMessageType.CREATE_SERVER.getMessage());

        return SQSRestServerBuilder
                .withInterface(sqsProperties.getHost())
                .withPort(sqsProperties.getPort())
                .start();
    }
}
