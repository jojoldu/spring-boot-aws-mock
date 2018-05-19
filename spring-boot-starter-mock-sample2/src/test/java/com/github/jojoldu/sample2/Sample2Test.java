package com.github.jojoldu.sample2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 23.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class Sample2Test {

    @Autowired
    private SampleListener listener;

    @Autowired
    private QueueMessagingTemplate messagingTemplate;

    @Test
    public void default_listener_test() throws Exception {
        //given
        this.listener.setCountDownLatch(new CountDownLatch(1));

        //when
        messagingTemplate.convertAndSend("sample", "sample: "+ LocalDate.now().toString());

        //then
        assertTrue(this.listener.getCountDownLatch().await(15, TimeUnit.SECONDS));
    }
}
