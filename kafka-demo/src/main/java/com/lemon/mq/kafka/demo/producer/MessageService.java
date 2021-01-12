package com.lemon.mq.kafka.demo.producer;

/**
 * @Author 李荣许
 * @create 2020/12/23
 */
public interface MessageService {

    /**
     * 发送消息
     * @param message
     */
    void sendMessage(String message);
}
