package com.lemon.mq.rocketmq.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lemon
 * @create 2021/1/29
 */
@RestController
@RequestMapping("/v1")
public class MsgController {

    @GetMapping("/send_msg")
    public void sendMsg(String msg) {

    }
}
