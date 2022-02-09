# 构建镜像
## docker build -f Dockerfile -t task:1.0.0 .
# 创建容器
## docker run -d -p 8004:8004 --name task --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.130 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/my-task/:/home/ap/logs/my-task task:1.0.0
## docker run -d -p 8004:8004 --name task --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.131 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/my-task/:/home/ap/logs/my-task task:1.0.0
## docker run -d -p 8004:8004 --name task --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.132 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/my-task/:/home/ap/logs/my-task task:1.0.0
