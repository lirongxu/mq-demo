package com.lemon.mq.kafka.demo.consumer;

import com.lemon.mq.kafka.demo.config.ConnectionConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @Author 李荣许
 * @create 2020/12/23
 */
@Slf4j
@Component
public class SimpleConsumer implements ApplicationRunner {
    @Resource
    private ConnectionConfig connectionConfig;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Properties properties = connectionConfig.getConnection();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-group-0");
        properties.put("enable.auto.commit", true);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singleton("test"));
        try {
            while (true) {
                boolean flag = true;
                //拉取信息，超时时间100ms
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1));
                //遍历打印消息
                for (ConsumerRecord<String, String> record : records) {
                    log.info("simple consumer: topic:{}, partition:{}, key:{}, value:{}", record.topic(), record.partition(), record.key(), record.value());
                    //消息发送完成
                    if (record.value().equals("done")) { flag = false; }
                }
                if (!flag) { break; }
            }
        } finally {
            consumer.close();
        }
    }
}
