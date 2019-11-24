package com.github.jojoldu.sqs;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.github.jojoldu.sqs.annotation.server.MockServerMessageType;
import com.github.jojoldu.sqs.config.SqsQueues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jojoldu@gmail.com on 24/11/2019
 * Blog : http://jojoldu.tistory.com
 * Github : http://github.com/jojoldu
 */
@Slf4j
@RequiredArgsConstructor
public class MockQueueGenerator {

    public static void generate(boolean isMockServerEnabled, String sqsEndPoint, SqsQueues queues) {
        AmazonSQSAsync sqsClient = getSqsClient(sqsEndPoint);

        if(isMockServerEnabled) {
            log.info(MockServerMessageType.CREATE_SERVER.getMessage());
            queues.getQueues().forEach(queue -> sqsClient.createQueue(queue.createQueueRequest()));
        } else {
            log.info(MockServerMessageType.USE_SERVER.getMessage());
        }

    }

    public static AmazonSQSAsync getSqsClient(String sqsEndPoint) {
        return AmazonSQSAsyncClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                    .withEndpointConfiguration(new EndpointConfiguration(sqsEndPoint, ""))
                    .build();
    }
}
