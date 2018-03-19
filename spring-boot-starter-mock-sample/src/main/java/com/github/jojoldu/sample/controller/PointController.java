package com.github.jojoldu.sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jojoldu.sample.domain.PointRepository;
import com.github.jojoldu.sample.dto.PointDto;
import com.github.jojoldu.sqs.config.SqsQueues;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 15.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@AllArgsConstructor
@RestController
public class PointController {
    private QueueMessagingTemplate messagingTemplate;
    private SqsQueues sqsQueues;
    private PointRepository pointRepository;
    private ObjectMapper objectMapper;

    @PostMapping("/point")
    public String save(@RequestBody PointDto requestDto){
        messagingTemplate.convertAndSend(sqsQueues.getQueueName("point"), requestDto);
        return "success";
    }

    @SqsListener(value = "${sqs.queues.point.name}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void receive(String message, @Header("SenderId") String senderId, Acknowledgment ack) throws IOException {
        log.info("senderId: {}, message: {}", senderId, message);
        PointDto messageObject = objectMapper.readValue(message, PointDto.class);
        try{
            pointRepository.save(messageObject.toEntity());
            ack.acknowledge().get();
        } catch (Exception e){
            log.error("Point Save Fail: "+ message, e);
        }
    }
}
