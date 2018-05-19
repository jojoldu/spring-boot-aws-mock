package com.github.jojoldu.sample2;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 23.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */
@Slf4j
@Component
public class SampleListener {

    @Getter
    @Setter
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @SqsListener(value = "sample", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void receive(String message, @Header("SenderId") String senderId, Acknowledgment ack) throws IOException, ExecutionException, InterruptedException {
        log.info("senderId: {}, message: {}", senderId, message);
        ack.acknowledge();
        countDownLatch.countDown();
    }
}
