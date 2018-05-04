package com.github.jojoldu.sqs.config;

import org.springframework.util.StringUtils;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 15.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class SqsProperties {

    public static final String DEFAULT_HOST = "localhost";
    public static final Integer DEFAULT_PORT = 9324;

    private String host;
    private Integer port;

    public String getEndPoint() {
        return String.format("http://%s:%s", getHost(), getPort());
    }

    public String getHost() {
        return StringUtils.isEmpty(host)? DEFAULT_HOST : host;
    }

    public Integer getPort() {
        return port == null? DEFAULT_PORT: port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
