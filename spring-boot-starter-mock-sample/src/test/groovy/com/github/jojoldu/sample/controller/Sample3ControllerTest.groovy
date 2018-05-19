package com.github.jojoldu.sample.controller

import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.model.GetQueueUrlRequest
import com.amazonaws.services.sqs.model.Message
import com.amazonaws.services.sqs.model.ReceiveMessageRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.jojoldu.sample.domain.PointRepository
import com.github.jojoldu.sample.dto.PointDto
import com.github.jojoldu.sample.listener.Sample3Listener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

import static org.mockito.ArgumentMatchers.any
import static org.mockito.BDDMockito.given

/**
 * Created by jojoldu@gmail.com on 2018. 3. 16.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Sample3ControllerTest extends Specification {

    @MockBean
    PointRepository pointRepository

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    AmazonSQSAsync sqs

    @Autowired
    Sample3Listener pointListener

    def "Ack fails, Go to the dlq."() {
        given:
        PointDto requestDto = PointDto.builder()
                .userId(1L)
                .savePoint(1000L)
                .description("buy laptop")
                .build()

        given(pointRepository.save(any()))
                .willThrow(new IllegalArgumentException("fail"))

        pointListener.setCountDownLatch(new CountDownLatch(1))

        when:
        restTemplate.postForEntity("/sample3", requestDto, String.class)

        then:
        this.pointListener.getCountDownLatch().await(15, TimeUnit.SECONDS)
    }

    List<Message> getMessagesFromQueue(String queueName) {
        String queueUrl = getQueueUrl(queueName)
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl)
        return sqs.receiveMessage(receiveMessageRequest).getMessages()
    }

    String getQueueUrl(String queueName) {
        GetQueueUrlRequest getQueueUrlRequest = new GetQueueUrlRequest(queueName)
        return this.sqs.getQueueUrl(getQueueUrlRequest).getQueueUrl()
    }

}
