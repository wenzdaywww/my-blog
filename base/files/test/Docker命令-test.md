# 构建镜像
## docker build -f Dockerfile -t base:1.0.0 .
# 创建容器
## docker run -d -p 8070:8070 --name base --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.130 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/my-base/:/home/ap/logs/my-base -v /home/ap/upload/my-base/:/home/ap/upload/my-base/ base:1.0.0
## docker run -d -p 8070:8070 --name base --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.131 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/my-base/:/home/ap/logs/my-base -v /home/ap/upload/my-base/:/home/ap/upload/my-base/ base:1.0.0
## docker run -d -p 8070:8070 --name base --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.132 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/my-base/:/home/ap/logs/my-base -v /home/ap/upload/my-base/:/home/ap/upload/my-base/ base:1.0.0
