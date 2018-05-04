package com.github.jojoldu.sqs.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by jojoldu@gmail.com on 2018. 5. 4.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
@AllArgsConstructor
public enum MockServerMessageType {
    USE_SERVER("Use Created Mock Sqs Server"),
    CREATE_SERVER("Create Mock Sqs Server");

    private String message;
}
