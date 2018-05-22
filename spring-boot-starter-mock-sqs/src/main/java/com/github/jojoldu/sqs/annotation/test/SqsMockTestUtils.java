package com.github.jojoldu.sqs.annotation.test;

import com.github.jojoldu.sqs.exception.SqsMockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.ServerSocket;

import static com.github.jojoldu.sqs.config.SqsProperties.DEFAULT_PORT;

/**
 * Created by jojoldu@gmail.com on 2018. 5. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
public class SqsMockTestUtils {

    private static Integer port;

    public static int getOrCreatePort(String propertyPort){
        if(port != null){
            return port;
        }
        int resultPort;
        if(StringUtils.isEmpty(propertyPort)) {
            resultPort = DEFAULT_PORT;
        } else if ("random".equals(propertyPort)){
            resultPort = SqsMockTestUtils.findAvailablePort();
        } else {
            resultPort = Integer.parseInt(propertyPort);
        }
        port = resultPort;
        return resultPort;
    }

    public static int findAvailablePort() {
        for (int port = 10000; port <= 60000; port++) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                return serverSocket.getLocalPort();
            } catch (IOException ex) {
            }
        }

        String message = "Not Found Available port: 10000 ~ 60000";
        log.error(message);
        throw new SqsMockException(message);
    }
}
