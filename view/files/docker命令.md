# 将dist文件夹、default.conf、Dockerfile文件上传
# 构建镜像
## docker build -f Dockerfile -t view:1.0.0 .
# 创建容器
## docker run -d -p 80:80 --name view -v /etc/localtime:/etc/localtime:ro --restart always view:1.0.0
