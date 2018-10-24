package com.github.jojoldu.sqs.annotation.utils;

import org.junit.Test;

import java.net.ServerSocket;

import static org.junit.Assert.assertTrue;

/**
 * Created by jojoldu@gmail.com on 2018. 5. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class RandomPortFinderTest {

    @Test
    public void get_available_port() throws Exception {
        //given
        int usePort = 10000;
        ServerSocket serverSocket = new ServerSocket(usePort);

        //when
        int availablePort = RandomPortFinder.findAvailablePort();

        //then
        assertTrue(availablePort != usePort);
        serverSocket.close();
    }

    @Test
    public void 포트는_10000에서_60000사이여야한다() {
        for(int i=0; i<10000;i++) {
            int port = RandomPortFinder.getRandomPort();
            assertTrue(port > 10000);
            assertTrue(port <= 60000);
        }
    }
}
