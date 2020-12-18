package com.lemon.mq.rabbitmq.demo.handler;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author 李荣许
 * @create 2020/12/16
 */
public interface MessageHandler {

    /**
     * 发送简单work queue消息
     * @param message
     * @throws IOException
     * @throws TimeoutException
     */
    void sendWorkMessage(String message) throws IOException, TimeoutException;

    /**
     * 发送 发布/订阅 消息
     * @param message
     * @throws IOException
     * @throws TimeoutException
     */
    void sendSubMessage(String message) throws IOException, TimeoutException;

    /**
     * 发送 routing 消息
     * @param routingKey
     * @param message
     * @throws IOException
     * @throws TimeoutException
     */
    void sendRoutingMessage(String routingKey, String message) throws IOException, TimeoutException;

    /**
     * 发送 topic 消息
     * @param message
     * @throws IOException
     * @throws TimeoutException
     */
    void sendTopicMessage(String routingKey, String message) throws IOException, TimeoutException;

    /**
     * 发送 rpc 消息
     * @param message
     * @throws IOException
     * @throws TimeoutException
     */
    void sendRpcMessage(String message) throws IOException, TimeoutException;

    /**
     * 发送
     * @param routingKey
     * @param message
     * @throws IOException
     * @throws TimeoutException
     */
    void sendDeadLetterMessage(String routingKey, String message) throws IOException, TimeoutException;
}
