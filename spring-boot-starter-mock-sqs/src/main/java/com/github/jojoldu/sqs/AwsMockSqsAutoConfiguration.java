package com.github.jojoldu.sqs;

import com.github.jojoldu.sqs.annotation.server.ConditionalOnMockSqs;
import com.github.jojoldu.sqs.config.SqsProperties;
import com.github.jojoldu.sqs.config.SqsQueues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@Configuration
@ConditionalOnMockSqs
public class AwsMockSqsAutoConfiguration {

    @Bean
    @ConfigurationProperties("sqs")
    public SqsQueues sqsQueues() {
        return new SqsQueues();
    }

    @Bean
    @ConfigurationProperties("sqs.mock")
    public SqsProperties sqsProperties() {
        return new SqsProperties();
    }

}
