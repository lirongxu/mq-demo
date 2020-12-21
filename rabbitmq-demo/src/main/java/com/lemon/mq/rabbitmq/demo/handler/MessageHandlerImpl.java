package com.lemon.mq.rabbitmq.demo.handler;

import com.lemon.mq.rabbitmq.demo.config.ConnectionConfig;
import com.lemon.mq.rabbitmq.demo.constant.QueueConstant;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author 李荣许
 * @create 2020/12/16
 */
@Service
public class MessageHandlerImpl implements MessageHandler {
    @Resource
    private ConnectionConfig connectionConfig;

    @Override
    public void sendWorkMessage(String message) throws IOException, TimeoutException{
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QueueConstant.SIMPLE_QUEUE_NAME, false, false, false, null);
        /**
         * exchange 消息要发送的交换器
         * routingKey 路由key
         * mandatory 如果mandatory标记被设置
         * properties 消息属性
         * body 消息体
         */
        channel.basicPublish("", QueueConstant.SIMPLE_QUEUE_NAME, null, message.getBytes());
        channel.close();
    }

    @Override
    public void sendSubMessage(String message) throws IOException, TimeoutException{
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(QueueConstant.EXCHANGE_NAME_FANOUT, BuiltinExchangeType.FANOUT);
        channel.basicPublish(QueueConstant.EXCHANGE_NAME_FANOUT, "", null, message.getBytes());
        channel.close();
    }

    @Override
    public void sendRoutingMessage(String routingKey, String message) throws IOException, TimeoutException{
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(QueueConstant.EXCHANGE_NAME_DIRECT, BuiltinExchangeType.DIRECT);
        channel.basicPublish(QueueConstant.EXCHANGE_NAME_DIRECT, routingKey, null, message.getBytes());
        channel.close();
    }

    @Override
    public void sendTopicMessage(String routingKey, String message) throws IOException, TimeoutException{
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(QueueConstant.EXCHANGE_NAME_TOPIC, BuiltinExchangeType.TOPIC);
        channel.basicPublish(QueueConstant.EXCHANGE_NAME_TOPIC, routingKey, null, message.getBytes());
        channel.close();
    }

    @Override
    public void sendRpcMessage(String message) throws IOException, TimeoutException {

    }

    @Override
    public void sendDeadLetterMessage(String routingKey, String message) throws IOException, TimeoutException {
        Connection connection = connectionConfig.getRabbitMqConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(QueueConstant.EXCHANGE_NAME_TEST_DEAD_LETTER, BuiltinExchangeType.TOPIC);

        /**
         * 消息10s过期
         */
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .contentEncoding("UTF-8")
                .expiration("10000")
                .build();
        channel.basicPublish(QueueConstant.EXCHANGE_NAME_TEST_DEAD_LETTER, routingKey, properties, message.getBytes());
        channel.close();
    }
}