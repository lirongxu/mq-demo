package com.lemon.mq.rabbitmq.demo.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * @Author 李荣许
 * @create 2020/12/15
 */
@Slf4j
@Configuration
@Import(RabbitmqConfig.class)
public class ConnectionConfig {
    @Resource
    private RabbitmqConfig rabbitmqConfig;

    @Bean
    public Connection rabbitMqConnection() {
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(rabbitmqConfig.getHost());
            connectionFactory.setPort(rabbitmqConfig.getPort());
            connectionFactory.setVirtualHost(rabbitmqConfig.getVirtualHost());
            connectionFactory.setUsername(rabbitmqConfig.getUsername());
            connectionFactory.setPassword(rabbitmqConfig.getPassword());
            Connection connection = connectionFactory.newConnection();
            return connection;
        } catch (Exception ex) {
            log.error("rabbitmq connect exception:{}", ex);
            return null;
        }
    }
}
