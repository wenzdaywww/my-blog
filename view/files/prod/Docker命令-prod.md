# 1、上传打包后的dist文件夹到/home/www/nginx/root
# 2、上传view.conf到/home/www/nginx/conf/conf.d
# 3、上传nginx.conf到/home/www/nginx/conf
# 4、创建nginx容器
## docker run -d --name nginx --restart always -p 8000:8000 -v /etc/localtime:/etc/localtime:ro -v /home/www/nginx/root:/usr/share/nginx/html -v /home/www/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /home/www/nginx/conf/conf.d/:/etc/nginx/conf.d/ docker.io/nginx
# 5、重新部署view只需将dist文件夹重新上传到/home/ap/nginx/root后重启nginx或者重新加载nginx
