# 构建镜像
## docker build -f Dockerfile -t task:1.0.0 .
# 创建容器,1.2.3.4到时候替换成云服务器IP
## docker run -d -p 8082:8082 --name task --restart always -e EUREKA_INSTANCE_IP-ADDRESS=1.2.3.4 -v /etc/localtime:/etc/localtime:ro -v /home/www/logs/my-task/:/home/www/logs/my-task task:1.0.0
