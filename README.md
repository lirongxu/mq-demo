## mq技术栈
### rabbitmq
#### rabbitmq安装
为了能减少安装花费时间本地采用docker-compose方式
先创建目录：
```
# cd /home/work/docker-compose/rabbitmq
# vi docker-compose.yml
```
编写docker-compose资源清单：
```
version: '3'
services:
  rabbitmq:
    image: rabbitmq:3.8.3-management
    container_name: rabbitmq
    restart: always
    hostname: myRabbitmq
    ports:
      - 15672:15672
      - 5672:5672
      - 25672:25672
    volumes:
      - ./data:/var/lib/rabbitmq
      - ./log:/var/log/rabbitmq/log
    environment:
      - RABBITMQ_DEFAULT_USER=root
      - RABBITMQ_DEFAULT_PASS=root
```
后台启动rabbitmq：
```
# docker-compose up -d
```
查看容器启动日志：
```
# docker ps
  Name                Command               State                                                     Ports
-------------------------------------------------------------------------------------------------------------------------------------------------------------
rabbitmq   docker-entrypoint.sh rabbi ...   Up      15671/tcp, 0.0.0.0:15672->15672/tcp, 0.0.0.0:25672->25672/tcp, 4369/tcp, 5671/tcp, 0.0.0.0:5672->5672/tcp
# docker logs -f [container id]
```
启动成功后访问下管理端:
**http://127.0.0.1:15672**

#### rabbitmq使用

### kafka
#### kafka安装
kafka安装采用docker-compose方式；安装单节点集群
```
# cd /home/work/docker-compose/kafka
# vi docker-compose.yml
```
编写docker-compose资源清单：
```
version: '2'
services:
  zookeeper:
    image: zookeeper:3.5.8
    volumes:
      - ./data:/data
    ports:
      - "2181:2181"
  kafka:
    image: wurstmeister/kafka:2.12-2.2.2
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 0
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://10.3.16.95:9092
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_AUTO_CREATE_TOPICS_ENABLE: 'true'
      KAFKA_CREATE_TOPICS: "test:2:1"
      KAFKA_HEAP_OPTS: -Xmx256M -Xms128M
    volumes:
      - ./var/run/docker.sock:/var/run/docker.sock
      - ./logs/:/opt/kafka/logs/
    depends_on:
      - zookeeper
  kafka-manager:
    image: kafkamanager/kafka-manager:3.0.0.4
    ports:
      - 9000:9000
    environment:
      ZK_HOSTS: zookeeper:2181
      KAFKA_BROKERS: kafka:9092
      KAFKA_MANAGER_AUTH_ENABLED: "true"
      KAFKA_MANAGER_USERNAME: admin
      KAFKA_MANAGER_PASSWORD: admin
    depends_on:
      - kafka
      - zookeeper
```
查看kafka运行
```
# docker-compose ps
        Name                       Command               State                          Ports
---------------------------------------------------------------------------------------------------------------------
kafka_kafka-manager_1   cmak-3.0.0.4/bin/cmak            Up      0.0.0.0:9000->9000/tcp
kafka_kafka_1           start-kafka.sh                   Up      0.0.0.0:32769->9092/tcp
kafka_zookeeper_1       /docker-entrypoint.sh zkSe ...   Up      0.0.0.0:2181->2181/tcp, 2888/tcp, 3888/tcp, 8080/tcp
```
通过kafka-admin查看kafka集群情况
http://127.0.0.1:9000
注册下kafka，就可以看到里面的topic等信息
KAFKA_ADVERTISED_LISTENERS 配置的是对外暴露的ip；(需要docker中zk可以访问以及容器外部可以访问)只能用宿主机的内网ip；也就是ifconfig的en0
