package com.github.jojoldu.sqs.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 17.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

/**
 * 이미 Mock SQS 서버가 실행중인 경우
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 40)
class OnMissingMockSqsServerCondition extends OnMockSqsServerBaseCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean isRunning = isRunning(context);
        return new ConditionOutcome(isRunning, createMessage(isRunning));
    }
}
