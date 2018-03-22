package com.github.jojoldu.sqs.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;

import static com.github.jojoldu.sqs.annotation.MockServerConstant.MOCK_SERVER_EXIST;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Order(Ordered.HIGHEST_PRECEDENCE + 40)
class OnMockSqsServerCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String existMockServer = context.getEnvironment().getProperty(MOCK_SERVER_EXIST);
        boolean match = !"true".equals(existMockServer);
        return new ConditionOutcome(match, createMessage(match));
    }

    private String createMessage(boolean match){
        return match? "Create Mock Sqs Server" : "Use Created Mock Sqs Server";
    }
}
