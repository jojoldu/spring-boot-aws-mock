package com.github.jojoldu.sqs.annotation.test;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by jojoldu@gmail.com on 2018. 5. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Slf4j
public class RandomPortFinder {

    public static int findAvailablePort() throws IOException {
        for (int port = 10000; port <= 60000; port++) {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                return serverSocket.getLocalPort();
            } catch (IOException ex) {
            }
        }

        String message = "Not Found free port: 10000 ~ 60000";
        log.error(message);
        throw new IOException(message);
    }
}
