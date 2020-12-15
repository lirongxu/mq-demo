package com.lemon.mq.rabbitmq.demo.controller;

import com.lemon.mq.rabbitmq.demo.config.ConnectionConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/v1")
public class SimpleQueueController {
    @Resource
    private ConnectionConfig connectionConfig;

    private final static String QUEUE_NAME = "simple_queue";

    @GetMapping("/send")
    public void send(@RequestParam String message) throws IOException, TimeoutException {
        Connection connection = connectionConfig.rabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        channel.close();
        connection.close();
    }
}
