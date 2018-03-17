package com.github.jojoldu.sqs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Configuration
@ConfigurationProperties("sqs")
public class SqsQueueNames {
    private Map<String, String> queueNames;

    public String getQueue(String key) {
        String value = queueNames.get(key);
        if(StringUtils.isEmpty(value)){
            throw new IllegalArgumentException("Not Found Queue Name, name: "+key);
        }
        return value;
    }

    public Map<String, String> getQueueNames() {
        return queueNames;
    }

    public void setQueueNames(Map<String, String> queueNames) {
        this.queueNames = queueNames;
    }
}
