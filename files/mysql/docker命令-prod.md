# 1、创建1个mysql容器
## docker run -d -p 3306:3306 --restart always -v /etc/localtime:/etc/localtime:ro -v /home/www/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=www362412 --name mysql mysql --lower-case-table-names=1
