# 创建3个redis实例，一主两从哨兵模式
## 1、创建主redis容器
### docker run -d -p 6379:6379 --restart always --name redis-single-master -v /etc/localtime:/etc/localtime:ro -v /home/ap/redis/config/redis-single-master.conf:/etc/redis/redis.conf -v /home/ap/redis/data/single-master:/data redis redis-server /etc/redis/redis.conf
## 2、创建2个从redis容器
### docker run -d -p 6379:6379 --restart always --name redis-single-slave -v /etc/localtime:/etc/localtime:ro -v /home/ap/redis/config/redis-single-slave.conf:/etc/redis/redis.conf -v /home/ap/redis/data/single-slave:/data redis redis-server /etc/redis/redis.conf
## 3、查看主从配置是否开启成功
### 连接客户端
#### docker exec -it redis-single-master redis-cli
### 输入密码
#### 127.0.0.1:6379> auth 密码
### 查看主从信息
#### 127.0.0.1:6379> info replication
##### Replication
##### role:master
##### connected_slaves:2
##### slave0:ip=192.168.1.131,port=6379,state=online,offset=182,lag=1
##### slave1:ip=192.168.1.132,port=6379,state=online,offset=182,lag=1
##### master_failover_state:no-failover
##### master_replid:f52bdf5032950a3b7d77b2699edcbc3282de2fd1
##### master_replid2:0000000000000000000000000000000000000000
##### master_repl_offset:182
##### second_repl_offset:-1
##### repl_backlog_active:1
##### repl_backlog_size:1048576
##### repl_backlog_first_byte_offset:1
##### repl_backlog_histlen:182
## 4、创建哨兵实例（至少3个）
### docker run -d -p 26379:26379 --name sentinel_redis --restart always -v /etc/localtime:/etc/localtime:ro -v /home/ap/redis/config/sentinel-single.conf:/etc/redis/sentinel.conf redis redis-sentinel /etc/redis/sentinel.conf
## 5、查看哨兵状态
### docker exec -it sentinel_redis redis-cli -p 26379 info
#### Sentinel
#### sentinel_masters:1
#### sentinel_tilt:0
#### sentinel_running_scripts:0
#### sentinel_scripts_queue_length:0
#### sentinel_simulate_failure_flags:0
#### master0:name=mymaster,status=ok,address=192.168.1.130:6379,slaves=2,sentinels=3
