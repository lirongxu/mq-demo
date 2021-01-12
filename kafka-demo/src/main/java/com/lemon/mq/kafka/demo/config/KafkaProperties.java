package com.lemon.mq.kafka.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 李荣许
 * @create 2020/12/23
 */
@Configuration
@ConfigurationProperties(prefix = "kafka")
@Data
public class KafkaProperties {

    private String servers;
}
