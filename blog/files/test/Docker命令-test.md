# 构建镜像
## docker build -f Dockerfile -t blog:1.0.0 .
# 创建容器
## docker run -d -p 8080:8080 --name blog --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.130 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/my-blog/:/home/ap/logs/my-blog -v /home/ap/upload/my-blog/:/home/ap/upload/my-blog/ blog:1.0.0
## docker run -d -p 8080:8080 --name blog --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.131 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/my-blog/:/home/ap/logs/my-blog -v /home/ap/upload/my-blog/:/home/ap/upload/my-blog/ blog:1.0.0
## docker run -d -p 8080:8080 --name blog --restart always -e EUREKA_INSTANCE_IP-ADDRESS=192.168.1.132 -v /etc/localtime:/etc/localtime:ro -v /home/ap/logs/my-blog/:/home/ap/logs/my-blog -v /home/ap/upload/my-blog/:/home/ap/upload/my-blog/ blog:1.0.0
