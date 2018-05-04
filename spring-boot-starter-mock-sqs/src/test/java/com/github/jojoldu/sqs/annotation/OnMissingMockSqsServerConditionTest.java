package com.github.jojoldu.sqs.annotation;

import com.github.jojoldu.sqs.config.SqsProperties;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 2018. 5. 4.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class OnMissingMockSqsServerConditionTest {

    OnMissingMockSqsServerCondition condition;

    @Before
    public void setUp() throws Exception {
        condition = new OnMissingMockSqsServerCondition();
    }

    @Test
    public void 윈도우OS가_아닌것을_확인할수있다() {
        boolean isWindow = condition.isWindowsOS();
        assertThat(isWindow).isEqualTo(false);
    }

    @Test
    public void 현재_실행중인_프로세스가있는지_확인할수있다() {
        String port = String.valueOf(SqsProperties.DEFAULT_PORT);
        boolean isRunning = condition.isRunning(port);
        assertThat(isRunning).isEqualTo(false);
    }
}
