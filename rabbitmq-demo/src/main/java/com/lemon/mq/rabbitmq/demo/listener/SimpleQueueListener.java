package com.lemon.mq.rabbitmq.demo.listener;

import com.lemon.mq.rabbitmq.demo.config.ConnectionConfig;
import com.lemon.mq.rabbitmq.demo.constant.QueueConstant;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author 李荣许
 * @create 2020/12/16
 */
@Slf4j
@Component
public class SimpleQueueListener implements ApplicationRunner {
    @Resource
    private ConnectionConfig connectionConfig;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        consumerLister1();
        consumerLister2();
    }

    private void consumerLister1() throws Exception {
        log.info("=============init simple consumer1=========");
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QueueConstant.SIMPLE_QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        channel.basicConsume(QueueConstant.SIMPLE_QUEUE_NAME, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("consume1:{} , {}, {}, {}", consumerTag, envelope, properties, new String(body));
            }
        });
    }

    private void consumerLister2() throws Exception {
        log.info("=============init simple consumer2=========");
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QueueConstant.SIMPLE_QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        channel.basicConsume(QueueConstant.SIMPLE_QUEUE_NAME, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("consume2:{} , {}, {}, {}", consumerTag, envelope, properties, new String(body));
                // 手动ack
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }

}
