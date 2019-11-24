package com.github.jojoldu.sqs.config;

import com.github.jojoldu.sqs.annotation.utils.RandomPortFinder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import static com.github.jojoldu.sqs.annotation.utils.RandomPortFinder.findAvailablePort;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 15.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class SqsProperties {

    public static final String DEFAULT_HOST = "localhost";
    public static final Integer DEFAULT_PORT = 9324;

    private String host = DEFAULT_HOST;
    private Integer port = DEFAULT_PORT;
    private Boolean enabled = false;
    private Boolean randomPortEnabled = false;

    public String getEndPoint() {
        return String.format("http://%s:%s", getHost(), getPort());
    }
    /**
     * properties에 값이 없을 경우 setter를 호출하지 않음
     */
    public void setRandomPortEnabled(Boolean randomPortEnabled) {
        this.randomPortEnabled = randomPortEnabled;
        this.setRandomPort();
    }

    private void setRandomPort() {
        if(isRandomPortEnabled()) {
            this.port = findAvailablePort();
        }
    }

    public boolean isRandomPortEnabled(){
        return getRandomPortEnabled();
    }
    public boolean isEnabled() {
        return getEnabled();
    }
}
