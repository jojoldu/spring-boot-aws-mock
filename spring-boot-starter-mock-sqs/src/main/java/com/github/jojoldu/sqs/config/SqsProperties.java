package com.github.jojoldu.sqs.config;

import com.github.jojoldu.sqs.annotation.test.SqsMockTestUtils;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 15.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@Setter
@NoArgsConstructor
public class SqsProperties {

    public static final String DEFAULT_HOST = "localhost";
    public static final Integer DEFAULT_PORT = 9324;

    private String host;
    private String port;

    public String getEndPoint() {
        return String.format("http://%s:%s", getHost(), getPort());
    }

    public String getHost() {
        return StringUtils.isEmpty(host)? DEFAULT_HOST : host;
    }

    public Integer getPort() {
        return SqsMockTestUtils.getOrCreatePort(port);
    }

    private Integer getOrCreatePort(String port) {
        if(StringUtils.isEmpty(port)) {
            return DEFAULT_PORT;
        }
        if("random".equals(port)){
            return SqsMockTestUtils.findAvailablePort();
        }
        return Integer.parseInt(port);
    }

}
