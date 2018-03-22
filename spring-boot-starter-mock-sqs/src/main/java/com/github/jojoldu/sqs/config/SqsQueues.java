package com.github.jojoldu.sqs.config;

import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class SqsQueues {
    private List<QueueData> queues;

    public List<QueueData> getQueues() {
        return queues;
    }

    public void setQueues(List<QueueData> queues) {
        this.queues = queues;
    }
}
