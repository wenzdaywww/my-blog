# 构建镜像
## docker build -f Dockerfile -t blog:1.0.0 .
# 创建容器,1.2.3.4到时候替换成云服务器IP
## docker run -d -p 8091:8091 --name blog --restart always -e EUREKA_INSTANCE_IP-ADDRESS=1.2.3.4 -v /etc/localtime:/etc/localtime:ro -v /home/www/logs/my-blog/:/home/www/logs/my-blog -v /home/www/upload/my-blog/:/home/www/upload/my-blog/ blog:1.0.0
