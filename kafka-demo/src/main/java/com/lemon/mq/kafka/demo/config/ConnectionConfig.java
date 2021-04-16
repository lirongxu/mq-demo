package com.lemon.mq.kafka.demo.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * @Author 李荣许
 * @create 2020/12/23
 */
@Configuration
@Import(KafkaProperties.class)
public class ConnectionConfig {
    @Resource
    private KafkaProperties kafkaProperties;

    public Properties getConnection() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getServers());
        /**
         * 生产者消息头序列化方式
         */
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        /**
         * 消费者消息头反序列化方式
         */
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //自定义分区分配器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.lemon.mq.kafka.demo.consumer.CustomPartitioner");
        return properties;
    }

}
