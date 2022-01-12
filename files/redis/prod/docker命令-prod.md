# 创建1个redis实例
## 1、创建主redis容器
### docker run -d -p 6380:6379 --restart always --name redis -v /etc/localtime:/etc/localtime:ro -v /home/www/redis/config/redis-prod.conf:/etc/redis/redis.conf -v /home/www/redis/data/:/data redis redis-server /etc/redis/redis.conf
