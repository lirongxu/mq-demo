package com.lemon.mq.rabbitmq.demo.controller;

import com.lemon.mq.rabbitmq.demo.entity.Result;
import com.lemon.mq.rabbitmq.demo.handler.MessageHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/v1")
public class MessageController {
    @Resource
    private MessageHandler messageHandler;

    @GetMapping("/work/send")
    public Result workSend(@RequestParam String message) throws IOException, TimeoutException {
        messageHandler.sendWorkMessage(message);

        return Result.ok();
    }

    @GetMapping("/sub/send")
    public Result subSend(@RequestParam String message) throws IOException, TimeoutException {
        messageHandler.sendSubMessage(message);

        return Result.ok();
    }

    @GetMapping("/direct/send")
    public Result directSend(
            @RequestParam(value = "routing_key") String routingKey,
            @RequestParam String message
    ) throws IOException, TimeoutException {
        messageHandler.sendRoutingMessage(routingKey, message);

        return Result.ok();
    }

    @GetMapping("/topic/send")
    public Result topicSend(
            @RequestParam(value = "routing_key") String routingKey,
            @RequestParam String message
    ) throws IOException, TimeoutException {
        messageHandler.sendTopicMessage(routingKey, message);

        return Result.ok();
    }
}
