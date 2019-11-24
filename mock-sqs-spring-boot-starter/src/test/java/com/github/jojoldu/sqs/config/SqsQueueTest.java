package com.github.jojoldu.sqs.config;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 19.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class SqsQueueTest {

    @Test
    public void SqsQueue_default_attribute() {
        SqsQueues.SqsQueue queueData = new SqsQueues.SqsQueue();

        assertThat(queueData.getDefaultVisibilityTimeout()).isEqualTo(3L);
        assertThat(queueData.getDelay()).isEqualTo(0L);
        assertThat(queueData.getReceiveMessageWait()).isEqualTo(0L);
    }
}
