package com.github.jojoldu.sqs;

import com.github.jojoldu.sqs.config.SqsQueues;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 19.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Configuration
public class AwsSqsQueueAutoConfiguration {

    @Bean
    @ConfigurationProperties("sqs")
    public SqsQueues sqsQueues() {
        return new SqsQueues();
    }
}
