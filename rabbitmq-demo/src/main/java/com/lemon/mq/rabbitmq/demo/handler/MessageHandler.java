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
     */
    void sendWorkMessage(String message) throws IOException, TimeoutException;

    /**
     * 发送 发布/订阅 消息
     * @param message
     */
    void sendSubMessage(String message) throws IOException, TimeoutException;

    /**
     * 发送 routing 消息
     * @param routingKey
     * @param message
     */
    void sendRoutingMessage(String routingKey, String message) throws IOException, TimeoutException;

    /**
     * 发送 topic 消息
     * @param message
     */
    void sendTopicMessage(String routingKey, String message) throws IOException, TimeoutException;

    /**
     * 发送 rpc 消息
     * @param message
     */
    void sendRpcMessage(String message) throws IOException, TimeoutException;
}
