# 项目简介
## 我的博客
## 项目内容
### base：基础服务
### blog：博客服务
### view：前端UI
### common：公共包
### eureka：eureka服务注册中心
### zuul：zuul路由网关
### uaa：统一认证授权服务
### task：定时任务服务

# 延伸阅读
## 服务器部署
~~~~
+----------------------+----------------------+----------------------+-------------------+
| IP:192.168.1.130     | IP:192.168.1.130     | IP:192.168.1.132     | 云服务器           |
+----------------------+----------------------+----------------------+-------------------+
| mysql:3306           | mysql:3306           | mysql:3306           | mysql:3306        |
+----------------------+----------------------+----------------------+-------------------+
| redis:6379           | redis:6379           | redis:6379           | redis:6379        |
+----------------------+----------------------+----------------------+-------------------+
| redis-sentinel:26379 | redis-sentinel:26379 | redis-sentinel:26379 |                   |
+----------------------+----------------------+----------------------+-------------------+
| eureka:8002          | eureka:8002          | eureka:8002          | eureka:8000       |
+----------------------+----------------------+----------------------+-------------------+
| zuul:8001            | zuul:8001            | zuul:8001            | zuul:8001         |
+----------------------+----------------------+----------------------+-------------------+
| uaa:8003             | uaa:8003             | uaa:8003             | uaa:8002          |
+----------------------+----------------------+----------------------+-------------------+
| base:8070            | base:8070            | base:8070            | base:8080         |
+----------------------+----------------------+----------------------+-------------------+
| view & nginx:80      | view & nginx:80      | view & nginx:80      | view & nginx:80   |
+----------------------+----------------------+----------------------+-------------------+
| keepalived           | keepalived           | keepalived           |                   |
+----------------------+----------------------+----------------------+-------------------+
| blog:8080            | blog:8080            | blog:8080            | blog:8081         |
+----------------------+----------------------+----------------------+-------------------+
|                      |                      |                      | task:8082         |
+----------------------+----------------------+----------------------+-------------------+
~~~~
## 各服务及应用的部署参看各包的files文件夹的说明