# 项目介绍
## 公共方法包
### 1、application.yml需要配置的参数
#### file.imgUrlPath 配置访问图片的相对路径
#### file.imgSavePath #图片保存的绝对路径，window的路径必须是\且路径最后也需要\
#### file.otherUrlPath #配置访问图片外其他文件的相对路径
#### file.otherSavePath #图片外其他文件保存的绝对路径，window的路径必须是\且路径最后也需要\
#### jwt.user-prefix #redis单点登录的token的用户key前缀
#### jwt.secret-key #token加密密钥
#### jwt.expire-time-second #token过期时间（秒）
#### jwt.cookie-day #cookie免登录有效天数
### 2、需要实现com.www.myblog.common.config.security.ISecurityServie接口
### 3、如果登录/login请求参数rmb=1，则token过期时间以jwt.cookie-day为准，否则以jwt.expire-time-second为准