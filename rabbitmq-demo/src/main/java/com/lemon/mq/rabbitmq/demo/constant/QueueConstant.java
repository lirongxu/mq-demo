package com.lemon.mq.rabbitmq.demo.constant;

/**
 * @Author 李荣许
 * @create 2020/12/16
 */
public class QueueConstant {

    /**
     * 简单队列：
     * 1、点对点
     * 2、多消费者争抢消费
     */
    public static final String SIMPLE_QUEUE_NAME = "simple_queue";

    /**
     * 发布订阅 模型
     */
    public static final String EXCHANGE_NAME_FANOUT = "exchange_fanout";

    /**
     * direct 模式
     */
    public static final String EXCHANGE_NAME_DIRECT = "exchange_direct";

    /**
     * 发布订阅 队列名称
     */
    public static final String SUBSCRIBE_QUEUE_NAME = "subscribe_queue_%s";
}
