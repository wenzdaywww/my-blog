# 构建镜像
## docker build -f Dockerfile -t uaa:1.0.0 .
# 创建容器,1.2.3.4到时候替换成云服务器IP
## docker run -d -p 8003:8003 --name uaa --restart always -e EUREKA_INSTANCE_IP-ADDRESS=1.2.3.4 -v /etc/localtime:/etc/localtime:ro -v /home/www/logs/uaa/:/home/www/logs/uaa uaa:1.0.0
