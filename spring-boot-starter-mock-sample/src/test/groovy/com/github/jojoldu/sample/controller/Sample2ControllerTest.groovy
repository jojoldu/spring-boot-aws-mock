package com.github.jojoldu.sample.controller

import com.github.jojoldu.sample.domain.Point
import com.github.jojoldu.sample.domain.PointRepository
import com.github.jojoldu.sample.dto.PointDto
import com.github.jojoldu.sample.listener.Sample2Listener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


/**
 * Created by jojoldu@gmail.com on 2018. 3. 16.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Sample2ControllerTest extends Specification {

    @Autowired
    Sample2Listener sample2Listener

    @Autowired
    PointRepository pointRepository

    @Autowired
    TestRestTemplate restTemplate

    void cleanup() {
        pointRepository.deleteAllInBatch()
    }

    def "Earn points through the queue."() {
        given:
        PointDto requestDto = PointDto.builder()
                .userId(10L)
                .savePoint(1000L)
                .description("buy laptop")
                .build()

        sample2Listener.setCountDownLatch(new CountDownLatch(1))

        when:
        ResponseEntity<String> response = restTemplate.postForEntity("/sample2", requestDto, String.class)

        then:
        this.sample2Listener.getCountDownLatch().await(15, TimeUnit.SECONDS)
        response.getStatusCode() == HttpStatus.OK
        List<Point> points = pointRepository.findAll()
        !points.isEmpty()
        points.get(0).getPoint() == 1000L
    }
}
