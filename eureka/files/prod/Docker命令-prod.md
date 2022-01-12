# 构建镜像
## docker build -f Dockerfile -t eureka:1.0.0 .
# 创建eureka容器,1.2.3.4到时候替换成云服务器IP
## docker run -d -p 8002:8002 --name eureka --restart always -e EUREKA_INSTANCE_IP-ADDRESS=1.2.3.4 -v /etc/localtime:/etc/localtime:ro -v /home/www/logs/eureka/:/home/www/logs/eureka eureka:1.0.0
