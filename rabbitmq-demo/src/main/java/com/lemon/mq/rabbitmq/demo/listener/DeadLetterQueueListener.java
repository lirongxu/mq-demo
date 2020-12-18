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
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 李荣许
 * @create 2020/12/18
 */
@Slf4j
@Component
public class DeadLetterQueueListener implements ApplicationRunner {
    @Resource
    private ConnectionConfig connectionConfig;

    /**
     * 模拟消息进入死信队列；进入死信队列的情况有：
     * 1、消息被拒绝(basic.reject / basic.nack)，并且requeue = false
     * 2、消息TTL过期
     * 3、队列达到最大长度
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 不给延迟队列设置消费者只消费死信队列就实现了延迟队列的功能
         */
        consumerLister1();
        consumerLister2();
    }

    private void consumerLister1() throws Exception {
        log.info("=============init test dead letter consumer=========");
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(QueueConstant.EXCHANGE_NAME_TEST_DEAD_LETTER, BuiltinExchangeType.TOPIC);
        Map<String, Object> arguments = new HashMap<>();
        //声明当前队列绑定死信交换机
        arguments.put("x-dead-letter-exchange", QueueConstant.EXCHANGE_NAME_DEAD_LETTER);
        //声明队列是个延时队列 延时60s
        arguments.put("x-message-ttl", 60000);
        channel.queueDeclare(QueueConstant.TEST_DEAD_LETTER_QUEUE_NAME, false, false, false, arguments);
        channel.queueBind(QueueConstant.TEST_DEAD_LETTER_QUEUE_NAME, QueueConstant.EXCHANGE_NAME_TEST_DEAD_LETTER, "mq.#");

        channel.exchangeDeclare(QueueConstant.EXCHANGE_NAME_DEAD_LETTER, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(QueueConstant.DEAD_LETTER_QUEUE_NAME, false, false, false, null);
        channel.queueBind(QueueConstant.DEAD_LETTER_QUEUE_NAME, QueueConstant.EXCHANGE_NAME_DEAD_LETTER, "#");

        channel.basicQos(1);
        channel.basicConsume(QueueConstant.TEST_DEAD_LETTER_QUEUE_NAME, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("[{}]consume:{} ,{} ,{}", QueueConstant.TEST_DEAD_LETTER_QUEUE_NAME, consumerTag, envelope, new String(body));
                channel.basicAck(envelope.getDeliveryTag(), true);
            }
        });
    }

    private void consumerLister2() throws Exception {
        log.info("=============init dead letter consumer=========");
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1);
        channel.basicConsume(QueueConstant.DEAD_LETTER_QUEUE_NAME, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                log.info("[{}]consume:{} ,{} ,{}", QueueConstant.DEAD_LETTER_QUEUE_NAME, consumerTag, envelope, new String(body));
            }
        });
    }
}
