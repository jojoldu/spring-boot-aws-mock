package com.github.jojoldu.sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jojoldu.sample.domain.PointRepository;
import com.github.jojoldu.sample.dto.PointDto;
import com.github.jojoldu.sample.listener.Sample3Listener;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Created by jojoldu@gmail.com on 2018. 5. 19.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class Sample3ControllerTest{

    @MockBean
    PointRepository pointRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    Sample3Listener pointListener;

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    @Test
    public void Ack_fails_Go_to_the_dlq() throws Exception {
        // given:
        PointDto requestDto = PointDto.builder()
                .userId(1L)
                .savePoint(1000L)
                .description("buy laptop")
                .build();

        given(pointRepository.save(any()))
                .willThrow(new IllegalArgumentException("fail"));

        pointListener.setCountDownLatch(new CountDownLatch(1));

        // when:
        messagingTemplate.convertAndSend("sample3", requestDto);

        // then:
        assertTrue(this.pointListener.getCountDownLatch().await(15, TimeUnit.SECONDS));
    }
}
