package com.github.jojoldu.sqs.exception;

import lombok.Getter;

/**
 * Created by jojoldu@gmail.com on 2018. 5. 4.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */

@Getter
public class SqsMockException extends RuntimeException {

    public SqsMockException(String message) {
        super(message);
    }

    public SqsMockException(String message, Throwable cause) {
        super(message, cause);
    }
}
