# 1、上传打包后的dist文件夹到/home/ap/nginx/root
# 2、上传view.conf到/home/ap/nginx/conf/conf.d
# 3、上传nginx.conf到/home/ap/nginx/conf
# 4、创建nginx容器
## docker run -d --name nginx --restart always -p 80:80 -v /etc/localtime:/etc/localtime:ro -v /home/ap/nginx/root:/usr/share/nginx/html -v /home/ap/nginx/conf/nginx.conf:/etc/nginx/nginx.conf -v /home/ap/nginx/conf/conf.d/:/etc/nginx/conf.d/ docker.io/nginx
# 5、重新部署view只需将dist文件夹重新上传到/home/ap/nginx/root后重启nginx或者重新加载nginx
# 6、配置nginx的高可用
## 6.1、在主机中安装keepalived
### yum install keepalived -y
### rpm -q -a keepalived #查看是否安装成功，安装成功后在/etc/keepalived中生成文件
### systemctl start/stop keepalived.service #启动或关闭keepalived
## 6.2、配置keepalived开机自启
### systemctl enable keepalived.service #开机自启
### systemctl disable keepalived.service #取消开机自启
## 6.3、以192.168.1.130为主服务器，192.168.1.131、192.168.1.132为备服务器为例，上传/master/keepalived.conf到130的/etc/keepalived中，
## 上传/backup/keepalived.conf到131和132的/etc/keepalived中，且将检查脚本nginx_check.sh分别上传到3台服务器的/etc/keepalived中
## 6.4、重启keepalived即可验证
