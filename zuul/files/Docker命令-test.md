# 构建镜像
## docker build -f Dockerfile -t zuul:1.0.0 .
# 创建容器
## docker run -d -p 8001:8001 --name zuul --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.130 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/zuul/:/home/ap/logs/zuul zuul:1.0.0
## docker run -d -p 8001:8001 --name zuul --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.131 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/zuul/:/home/ap/logs/zuul zuul:1.0.0
## docker run -d -p 8001:8001 --name zuul --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.132 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/zuul/:/home/ap/logs/zuul zuul:1.0.0
