package com.github.jojoldu.sqs.config;

import com.github.jojoldu.sqs.annotation.test.SqsMockUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 15.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@NoArgsConstructor
public class SqsProperties {

    public static final String DEFAULT_HOST = "localhost";
    public static final Integer DEFAULT_PORT = 9324;

    private String host;
    private Integer port;

    public String getEndPoint() {
        return String.format("http://%s:%s", getHost(), getPort());
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = SqsMockUtils.getOrCreatePort(port);
    }

    public String getHost() {
        return StringUtils.isEmpty(host)? DEFAULT_HOST : host;
    }

    /**
     * properties에 값이 없을 경우 setter를 호출하지 않음
     */
    public Integer getPort() {
        return port == null? DEFAULT_PORT: port;
    }

}
