# 构建镜像
## docker build -f Dockerfile -t base:1.0.0 .
# 创建容器,1.2.3.4到时候替换成云服务器IP
## docker run -d -p 8080:8080 --name base --restart always -e EUREKA_INSTANCE_IP-ADDRESS=1.2.3.4 -v /etc/localtime:/etc/localtime:ro -v /home/www/logs/my-base/:/home/www/logs/my-base -v /home/www/upload/my-base/:/home/www/upload/my-base/ base:1.0.0
