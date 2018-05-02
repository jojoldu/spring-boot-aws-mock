package com.github.jojoldu.sqs.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import static com.github.jojoldu.sqs.annotation.MockServerConstant.SQS_SERVER_PORT;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Order(Ordered.HIGHEST_PRECEDENCE + 40)
class OnMissingMockSqsServerCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String sqsServerPort = getSqsServerPort(context);
        boolean match = isRunning(sqsServerPort);
        return new ConditionOutcome(match, createMessage(match));
    }

    private String getSqsServerPort(ConditionContext context) {
        return Optional.ofNullable(context.getEnvironment().getProperty(SQS_SERVER_PORT))
                .orElse("9324");
    }

    private boolean isRunning(String port) {
        String line;
        StringBuilder pidInfo = new StringBuilder();
        try {
            String command = String.format("netstat -nat | grep LISTEN|grep %s", port);
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }
        } catch (IOException ioe) {

        }

        return !StringUtils.isEmpty(pidInfo.toString());
    }

    private String createMessage(boolean match) {
        return match ? "Use Created Mock Sqs Server" : "Created Mock Sqs Server";
    }
}
