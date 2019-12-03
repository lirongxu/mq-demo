package com.lemon.mq.rabbitmq.demo.instance;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleQueue {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String queueName,String message){
        amqpTemplate.convertAndSend(queueName,message);
    }
}
