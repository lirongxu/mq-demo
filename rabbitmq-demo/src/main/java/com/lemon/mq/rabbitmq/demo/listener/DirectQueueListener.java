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
public class DirectQueueListener implements ApplicationRunner {
    @Resource
    private ConnectionConfig connectionConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        consumerLister1();
        consumerLister2();
    }

    private void consumerLister1() throws Exception {
        log.info("=============init direct consumer1=========");
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(QueueConstant.EXCHANGE_NAME_DIRECT, BuiltinExchangeType.DIRECT);
        String queueName = String.format(QueueConstant.DIRECT_QUEUE_NAME, "1");
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, QueueConstant.EXCHANGE_NAME_DIRECT, "key1");
        channel.basicQos(1);
        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("[{}]consume1:{} ,{} ,{}", queueName, consumerTag, envelope, new String(body));
            }
        });
    }

    private void consumerLister2() throws Exception {
        log.info("=============init direct consumer2=========");
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(QueueConstant.EXCHANGE_NAME_DIRECT, BuiltinExchangeType.DIRECT);
        String queueName = String.format(QueueConstant.DIRECT_QUEUE_NAME, "2");
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, QueueConstant.EXCHANGE_NAME_DIRECT, "key2");
        channel.basicQos(1);
        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("[{}]consume2:{} ,{} ,{}", queueName, consumerTag, envelope, new String(body));
            }
        });
    }
}
