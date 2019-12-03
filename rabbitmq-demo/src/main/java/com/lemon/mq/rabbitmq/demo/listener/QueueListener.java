package com.lemon.mq.rabbitmq.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueueListener {
    @RabbitListener(queues = "lemon.queue")
    public void queueListener(String message){
        log.info("lemon.queue :{}", message);
    }
}
