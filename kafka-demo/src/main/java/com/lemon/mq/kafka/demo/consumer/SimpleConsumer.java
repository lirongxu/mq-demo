package com.lemon.mq.kafka.demo.consumer;

import com.lemon.mq.kafka.demo.config.ConnectionConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @Author 李荣许
 * @create 2020/12/23
 */
@Slf4j
@Component
public class SimpleConsumer implements ApplicationRunner {
    @Resource
    private ConnectionConfig connectionConfig;
    private static final String topic = "test";
    public boolean flag = true;
    private static ExecutorService executor = new ThreadPoolExecutor(2,2,0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2));

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 启动2个消费者去消费
         * 有2个分区2个消费者分别注册到0跟1分区上
         */
        executor.execute(() -> {
            consumerLister0();
        });
        executor.execute(() -> {
            consumerLister1();
        });
    }

    /**
     * 采用自动提交offset方式
     */
    private void consumerLister0() {
        log.info("=============init simple consumer0=========");
        Properties properties = connectionConfig.getConnection();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-group-0"); /* 消费组id */
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true); /* 启用offset自动提交 */
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000); /* 自动提交offset的周期(毫秒) 默认5000 */
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); /* 没有offset或者不正确的时候设置  latest earliest none */

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singleton(topic));
        try {
            Set<TopicPartition> topicPartitions = consumer.assignment(); /* consumer消费的通道与分区信息 */
            log.info("topicPartitions:{}", topicPartitions);
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1)); /*拉取信息，超时时间100ms*/
                //遍历打印消息
                for (ConsumerRecord<String, String> record : records) {
                    log.info("simple consumer0: topic:{}, partition:{}, key:{}, value:{}", record.topic(), record.partition(), record.key(), record.value());
                    //消息发送完成
                    if (record.value().equals("done")) { flag = false; }
                }
                if (!flag) { break; }
            }
        } finally {
            consumer.close();
        }
    }

    /**
     * 手动提交offset方式
     */
    private void consumerLister1() {
        log.info("=============init simple consumer1=========");
        Properties properties = connectionConfig.getConnection();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-group-0");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); /* 关闭offset自动提交 */

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singleton(topic));
        try {
            while (true) {
                //拉取信息，超时时间100ms
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1));
                //遍历打印消息
                for (ConsumerRecord<String, String> record : records) {
                    log.info("simple consumer1: topic:{}, partition:{}, key:{}, value:{}", record.topic(), record.partition(), record.key(), record.value());
                    //consumer.commitSync(); /*同步提交当前消费的offset*/
                    /**
                     * 异步提交offset 并监听回调结果，提交失败回调会有异常
                     */
                    consumer.commitAsync(new OffsetCommitCallback() {
                        @Override
                        public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
                            if(e == null) {
                                log.info("offset提交成功结果：{}", map);
                            } else{
                                log.info("offset提交失败:{}", e);
                            }
                        }
                    });
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
