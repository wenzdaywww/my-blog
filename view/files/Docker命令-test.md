# 1、上传打包后的dist文件夹到/home/ap/nginx/root
# 2、上传view在nginx中的配置view.conf到/home/ap/nginx/conf
# 3、创建nginx容器
## docker run -d --name nginx --restart always -p 80:80 -v /etc/localtime:/etc/localtime:ro -v /home/ap/nginx/root:/usr/share/nginx/html -v /home/ap/nginx/conf:/etc/nginx/conf.d docker.io/nginx
# 4、重新部署view只需将dist文件夹重新上传到/home/ap/nginx/root后重启nginx或者重新加载nginx
