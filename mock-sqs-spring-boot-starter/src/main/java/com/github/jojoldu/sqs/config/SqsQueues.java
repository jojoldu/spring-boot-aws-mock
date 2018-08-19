package com.github.jojoldu.sqs.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@Setter
@NoArgsConstructor
public class SqsQueues {
    private List<QueueData> queues;

    public void createQueue(AmazonSQSAsync sqsAsync){
        this.queues.forEach(queueData -> sqsAsync.createQueue(queueData.createQueueRequest()));
    }
}
