package com.github.jojoldu.sample.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jojoldu.sample.domain.PointRepository;
import com.github.jojoldu.sample.dto.PointDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by jojoldu@gmail.com on 2018. 5. 19.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class Sample2Listener {

    private final PointRepository pointRepository;
    private final ObjectMapper objectMapper;

    @Getter
    @Setter
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @SqsListener(value = "sample2", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void receive(String message, @Header("SenderId") String senderId, Acknowledgment ack) throws IOException {
        log.info("[sample2][Queue] senderId: {}, message: {}", senderId, message);
        PointDto messageObject = objectMapper.readValue(message, PointDto.class);
        pointRepository.save(messageObject.toEntity());
        ack.acknowledge();
        countDownLatch.countDown();
        log.info("[sample2] Success Ack");
    }
}
