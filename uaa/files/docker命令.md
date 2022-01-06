# 构建镜像
## docker build -f Dockerfile -t uaa:1.0.0 .
# 创建容器
## docker run -d -p 8003:8003 --name uaa --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.131 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/uaa/:/home/ap/logs/uaa uaa:1.0.0
