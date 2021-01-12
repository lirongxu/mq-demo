package com.lemon.mq.kafka.demo.config;

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
        properties.put("bootstrap.servers", kafkaProperties.getServers());
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //自定义分区分配器
        properties.put("partitioner.class", "com.lemon.mq.kafka.demo.consumer.CustomPartitioner");
        return properties;
    }

}
