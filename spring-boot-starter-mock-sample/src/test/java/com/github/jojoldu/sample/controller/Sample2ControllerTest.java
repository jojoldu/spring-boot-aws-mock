package com.github.jojoldu.sample.controller;

import com.github.jojoldu.sample.domain.Point;
import com.github.jojoldu.sample.domain.PointRepository;
import com.github.jojoldu.sample.dto.PointDto;
import com.github.jojoldu.sample.listener.Sample2Listener;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by jojoldu@gmail.com on 2018. 5. 19.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Sample2ControllerTest {

    @Autowired
    Sample2Listener sample2Listener;

    @Autowired
    PointRepository pointRepository;

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    @After
    public void cleanup() {
        pointRepository.deleteAllInBatch();
    }

    @Test
    public void Earn_points_through_the_queue() throws Exception {
        // given
        PointDto requestDto = PointDto.builder()
                .userId(10L)
                .savePoint(1000L)
                .description("buy laptop")
                .build();

        sample2Listener.setCountDownLatch(new CountDownLatch(1));

        // when
        messagingTemplate.convertAndSend("sample2", requestDto);

        // then
        assertTrue(this.sample2Listener.getCountDownLatch().await(30, TimeUnit.SECONDS));
        List<Point> points = pointRepository.findAll();
        assertThat(points.isEmpty()).isEqualTo(false);
        assertThat(points.get(0).getPoint()).isEqualTo(1000L);
    }
}
