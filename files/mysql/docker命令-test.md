# 1、创建3个mysql容器
## docker run -d -p 3306:3306 --restart always -v /etc/localtime:/etc/localtime:ro -v /home/ap/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=www362412 --name mysql mysql --lower-case-table-names=1
# 2、修改主数据库配置
## 2.1、进入mysql容器
### docker exec -it mysql /bin/bash
## 2.2、修改/etc/mysql/my.cnf文件，修改以下内容
### 如果此时vi或vim命令不能用，则执行下列命令
### apt-get update
### apt-get install vim
### 在my.cnf文件[mysqld]的位置添加一下内容，注=两边需要空格
#### server-id = 130 #同一局域网内注意server-id要唯一
#### log-bin = master-bin  #开启二进制日志功能，可以随便取（关键）
#### binlog-format = mixed     #二级制日志格式，可不配做，有三种 row(保存影响记录数据 )，statement(保存SQL语句)，mixed(前面两种的结合)
#### binlog-do-db  #同步的数据库名称,如果不配置，表示同步所有的库
#### binlog_ignore_db #忽略同步的数据库名称,如果不配置
## 2.3、配置完成后重启主数据库 
### docker restart mysql
# 3、主数据库创建同步账户
## 3.1、进入主数据库mysql容器
### docker exec -it mysql /bin/bash
## 3.2、进入mysql客户端,输入密码
### mysql -uroot -p
## 3.3、创建同步的用户，mysql8版本之前使用IDENTIFIED BY，mysql8使用IDENTIFIED WITH MYSQL_NATIVE_PASSWORD BY
### mysql> CREATE USER 'slave'@'%' IDENTIFIED WITH MYSQL_NATIVE_PASSWORD BY'www362412';
## 3.4、授权同步的用户
### mysql> GRANT REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'slave'@'%';
## 3.5、查看Master状态，记住File和Position，在从库中配置需要使用
### mysql> show master status;
### +-------------------+----------+--------------+------------------+-------------------+
### | File              | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
### +-------------------+----------+--------------+------------------+-------------------+
### | master-bin.000003 |      877 |              |                  |                   |
### +-------------------+----------+--------------+------------------+-------------------+
# 4、从数据库配置
## 4.1、进入容器
### docker exec -it mysql /bin/bash
## 4.2、进入mysql客户端,输入密码
### mysql -uroot -p
## 4.3、进入到Slave库myslq客户端，执行如下命令
### mysql> CHANGE MASTER TO MASTER_HOST='192.168.1.130', MASTER_USER='slave', MASTER_PASSWORD='www362412', MASTER_PORT=3306, MASTER_LOG_FILE='master-bin.000003', MASTER_LOG_POS=1, MASTER_CONNECT_RETRY=30;
#### master_host：Master库的地址，指的是容器的独立ip,可以通过
#### master_port：Master的端口号，指的是容器的端口号
#### master_user：用于数据同步的用户
#### master_password：用于同步的用户的密码
#### master_log_file：指定 Slave 从哪个日志文件开始复制数据，即上文中提到的 File 字段的值
#### master_log_pos：从哪个 Position 开始读，即上文中提到的 Position 字段的值
#### master_connect_retry：如果连接失败，重试的时间间隔，单位是秒，默认是60秒
## 4.4、开启主从复制过程
### mysql> start slave;
## 4.5、查看主从同步状态
### mysql> show slave status\G
####Slave_IO_Running和Slave_SQL_Running都是Yes，说明主从复制已经开启。此时可以测试数据同步是否成功。