package com.github.jojoldu.sqs.annotation.test;

import org.junit.Test;

import java.net.ServerSocket;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jojoldu@gmail.com on 2018. 5. 21.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

public class RandomPortFinderTest {

    @Test
    public void get_available_port() throws Exception {
        //given
        ServerSocket serverSocket = new ServerSocket(10000);

        //when
        int availablePort = SqsMockUtils.findAvailablePort();

        //then
        assertThat(availablePort).isEqualTo(10001);
        serverSocket.close();
    }
}
