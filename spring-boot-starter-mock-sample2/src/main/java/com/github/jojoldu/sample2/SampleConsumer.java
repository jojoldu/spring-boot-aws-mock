package com.github.jojoldu.sample2;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 23.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */
@Slf4j
@AllArgsConstructor
@RestController
public class SampleConsumer {

    @SqsListener(value = "sample", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void receive(String message, @Header("SenderId") String senderId, Acknowledgment ack) throws IOException, ExecutionException, InterruptedException {
        log.info("senderId: {}, message: {}", senderId, message);
        ack.acknowledge().get();
    }
}
