package com.lemon.mq.kafka.demo.producer.impl;

import com.alibaba.fastjson.JSONObject;
import com.lemon.mq.kafka.demo.config.ConnectionConfig;
import com.lemon.mq.kafka.demo.producer.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * @Author 李荣许
 * @create 2020/12/23
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private ConnectionConfig connectionConfig;
    private static KafkaProducer<String, String> producer;
    private static final String topic = "test";

    @Override
    public void sendMessage(String message) {
        Properties properties = connectionConfig.getConnection();
        producer = new KafkaProducer<String, String>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message, message);
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                log.info("recordMetadata:{}", JSONObject.toJSONString(recordMetadata));
            }
        });
        producer.close();
    }
}
