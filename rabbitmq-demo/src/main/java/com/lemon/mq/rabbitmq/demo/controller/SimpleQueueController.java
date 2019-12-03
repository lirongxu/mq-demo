package com.lemon.mq.rabbitmq.demo.controller;

import com.lemon.mq.rabbitmq.demo.instance.SimpleQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class SimpleQueueController {
    @Autowired
    private SimpleQueue simpleQueue;

    @GetMapping("/send")
    public void send(@RequestParam String message){
        simpleQueue.send("lemon.queue", message);
    }
}
