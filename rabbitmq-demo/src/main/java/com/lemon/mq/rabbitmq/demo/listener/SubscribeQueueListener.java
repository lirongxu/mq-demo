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
public class SubscribeQueueListener implements ApplicationRunner {
    @Resource
    private ConnectionConfig connectionConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        consumerLister1();
        consumerLister2();
        consumerLister3();
    }

    private void consumerLister1() throws Exception {
        log.info("=============init sub consumer1=========");
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(QueueConstant.EXCHANGE_NAME_FANOUT, BuiltinExchangeType.FANOUT);
        String queueName = String.format(QueueConstant.SUBSCRIBE_QUEUE_NAME, "1");
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, QueueConstant.EXCHANGE_NAME_FANOUT, "");
        channel.basicQos(1);
        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("[{}]consume1:{} ,{} ,{}", queueName, consumerTag, envelope, new String(body));
            }
        });
    }

    private void consumerLister2() throws Exception {
        log.info("=============init sub consumer2=========");
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        String queueName = String.format(QueueConstant.SUBSCRIBE_QUEUE_NAME, "2");
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, QueueConstant.EXCHANGE_NAME_FANOUT, "");
        channel.basicQos(1);
        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("[{}]consume2:{} ,{} ,{}", queueName, consumerTag, envelope, new String(body));
            }
        });
    }

    private void consumerLister3() throws Exception {
        log.info("=============init sub consumer3=========");
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        String queueName = String.format(QueueConstant.SUBSCRIBE_QUEUE_NAME, "3");
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, QueueConstant.EXCHANGE_NAME_FANOUT, "");
        channel.basicQos(1);
        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("[{}]consume3:{} ,{} ,{}", queueName, consumerTag, envelope, new String(body));
            }
        });
    }
}
