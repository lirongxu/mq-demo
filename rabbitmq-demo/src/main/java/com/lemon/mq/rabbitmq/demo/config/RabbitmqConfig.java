package com.lemon.mq.rabbitmq.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 李荣许
 * @create 2020/12/15
 */
@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class RabbitmqConfig {
    private String host;

    private Integer port;

    private String virtualHost;

    private String username;

    private String password;
}
