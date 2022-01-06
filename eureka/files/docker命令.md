# 构建镜像
## docker build -f Dockerfile -t eureka:1.0.0 .
# 创建eureka容器
## docker run -d -p 8002:8002 --name eureka --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.130 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/eureka/:/home/ap/logs/eureka eureka:1.0.0
## docker run -d -p 8002:8002 --name eureka --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.131 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/eureka/:/home/ap/logs/eureka eureka:1.0.0
## docker run -d -p 8002:8002 --name eureka --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.132 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/eureka/:/home/ap/logs/eureka eureka:1.0.0
