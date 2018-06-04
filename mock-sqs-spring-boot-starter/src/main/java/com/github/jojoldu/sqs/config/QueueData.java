package com.github.jojoldu.sqs.config;

import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 19.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@Setter
@NoArgsConstructor
public class QueueData {

    private String name;
    private Long defaultVisibilityTimeout = 3L;
    private Long delay = 0L;
    private Long receiveMessageWait = 0L;
    private DeadLettersQueueData deadLettersQueue = null;

    public CreateQueueRequest createQueueRequest(){
        return new CreateQueueRequest(name)
                .withAttributes(getAttributes());
    }

    public Map<String, String> getAttributes(){
        Map<String, String> attributes = new LinkedHashMap<>();
        attributes.put("VisibilityTimeout", String.valueOf(defaultVisibilityTimeout));
        attributes.put("DelaySeconds", String.valueOf(delay));
        attributes.put("ReceiveMessageWaitTimeSeconds", String.valueOf(receiveMessageWait));
        if(deadLettersQueue != null){
            attributes.put("RedrivePolicy", deadLettersQueue.toJson());
        }

        return attributes;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DeadLettersQueueData {
        private String name;
        private String maxReceiveCount;
        private String deadLetterTargetArn;

        public String toJson(){
            this.deadLetterTargetArn = "arn:aws:sqs:elasticmq:000000000000:"+name;
            try {
                return new ObjectMapper().writeValueAsString(this);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("JsonParseException", e);
            }
        }
    }
}
