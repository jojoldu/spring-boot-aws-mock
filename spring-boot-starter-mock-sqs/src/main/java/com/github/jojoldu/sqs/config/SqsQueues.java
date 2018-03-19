package com.github.jojoldu.sqs.config;

import java.util.Map;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class SqsQueues {
    private Map<String, QueueData> queues;

    public QueueData getQueue(String key) {
        return queues.get(key);
    }

    public String getQueueName(String key){
        return queues.get(key).getName();
    }

    public Map<String, QueueData> getQueues() {
        return queues;
    }

    public void setQueues(Map<String, QueueData> queues) {
        this.queues = queues;
    }
}
