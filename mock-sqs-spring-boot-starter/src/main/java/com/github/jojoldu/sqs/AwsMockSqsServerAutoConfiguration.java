package com.github.jojoldu.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.github.jojoldu.sqs.annotation.server.ConditionalOnMockSqs;
import com.github.jojoldu.sqs.annotation.server.ConditionalOnMockSqsServer;
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

import static com.github.jojoldu.sqs.MockQueueGenerator.generate;
import static com.github.jojoldu.sqs.MockQueueGenerator.getSqsClient;

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


    @Bean
    @DependsOn("sqsRestServer")
    @ConditionalOnMockSqsServer
    public AmazonSQSAsync amazonSqs() {
        return getSqsClient(sqsProperties.getEndPoint());
    }

    @Bean(destroyMethod="stopAndWait")
    @Primary
    @ConditionalOnMockSqsServer
    public SQSRestServer sqsRestServer() {
        SQSRestServer sqsServer = createMockSqsServer();

        generate(sqsProperties.isEnabled(), sqsProperties.getEndPoint(), sqsQueues);

        return sqsServer;
    }

    private SQSRestServer createMockSqsServer() {
        return SQSRestServerBuilder
                .withInterface(sqsProperties.getHost())
                .withPort(sqsProperties.getPort())
                .start();
    }

}
