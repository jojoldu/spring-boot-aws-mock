package com.github.jojoldu.sample.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Created by jojoldu@gmail.com on 2018. 3. 23.
 * Blog : http://jojoldu.tistory.com
 * Github : https://github.com/jojoldu
 */
@Slf4j
@AllArgsConstructor
@RestController
public class SampleProvider {

    @Autowired private QueueMessagingTemplate messagingTemplate;

    @GetMapping("/sample")
    public String save(){
        messagingTemplate.convertAndSend("sample", "sample: "+ LocalDate.now().toString());
        return "success";
    }
}
