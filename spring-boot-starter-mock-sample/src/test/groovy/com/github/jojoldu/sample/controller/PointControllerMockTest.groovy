package com.github.jojoldu.sample.controller

import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.model.GetQueueUrlRequest
import com.amazonaws.services.sqs.model.Message
import com.amazonaws.services.sqs.model.PurgeQueueRequest
import com.amazonaws.services.sqs.model.ReceiveMessageRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.jojoldu.sample.domain.PointRepository
import com.github.jojoldu.sample.dto.PointDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

import static org.mockito.BDDMockito.given
import static org.mockito.Matchers.anyObject

/**
 * Created by jojoldu@gmail.com on 2018. 3. 16.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PointControllerMockTest extends Specification {

    @MockBean
    PointRepository pointRepository

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    AmazonSQSAsync sqs

    static final def POINT_DLQ = "point-dlq"

    void cleanup() {
        String queueUrl = getQueueUrl(POINT_DLQ)
        PurgeQueueRequest queue = new PurgeQueueRequest(queueUrl)
        sqs.purgeQueue(queue)
    }

    def "ack실패하면 메세지는 Dead Letter Queue로 전송된다."() {
        given:
        PointDto requestDto = PointDto.builder()
                .userId(1L)
                .savePoint(1000L)
                .description("buy laptop")
                .build()

        given(pointRepository.save(anyObject()))
                .willThrow(new IllegalArgumentException("fail"))
        when:
        restTemplate.postForEntity("/point", requestDto, String.class)
        Thread.sleep(2000L)
        List<Message> result = getMessagesFromQueue(POINT_DLQ)

        then:
        result.size() == 1
        PointDto body = objectMapper.readValue(result.get(0).getBody(), PointDto.class)
        body.getUserId() == 1L
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
