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
# docker logs -f [container id]
```
启动成功后访问下管理端:
**http://127.0.0.1:15672**