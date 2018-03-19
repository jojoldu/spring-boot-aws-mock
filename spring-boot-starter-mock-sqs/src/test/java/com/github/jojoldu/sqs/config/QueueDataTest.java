package com.github.jojoldu.sqs.config;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 19.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class QueueDataTest {

    @Test
    public void QueueData_default_attribute() {
        QueueData queueData = new QueueData();

        assertThat(queueData.getDefaultVisibilityTimeout()).isEqualTo(3L);
        assertThat(queueData.getDelay()).isEqualTo(0L);
        assertThat(queueData.getReceiveMessageWait()).isEqualTo(0L);
    }
}
