# 项目介绍
## 公共方法包
### 1、要使用其中的配置或者服务，需要在启动类添加包扫描路径，
###如需要使用config包中配置@ComponentScan(basePackages = {"com.www.myblog.common.config","项目扫描包路径"})
### 2、application.yml需要配置的参数
#### file.imgUrlPath 配置访问图片的相对路径
#### file.imgSavePath #图片保存的绝对路径，window的路径必须是\且路径最后也需要\
#### file.otherUrlPath #配置访问图片外其他文件的相对路径
#### file.otherSavePath #图片外其他文件保存的绝对路径，window的路径必须是\且路径最后也需要\
### 3、如果使用（默认关闭）com.www.myblog.common.config.security中的单机security安全校验，需要把注释掉的注解打开，还需要实现
### com.www.myblog.common.config.security.ISecurityServie的方法，且application.yml需要配置以下参数
#### jwt.user-prefix 使用redis保存用户的token的key前缀
#### jwt.secret-key jwt令牌签名
#### jwt.expire-time-second 过期时间（秒）
#### jwt.cookie-day cookie免登录有效天数
### 4、默认使用com.www.myblog.common.config.oauth2中的分布式统一资源服务器安全校验，需要实现
### com.www.myblog.common.config.oauth2.IOauth2Service的方法，且application.yml需要配置以下参数
#### spring.application.name 项目名称，这边使用项目名称作为oauth2认证服务器中的resourceId
#### security.oauth2.authorization.jwt.key-value 解析jwt生成的token的签名
#### 注：用户拥有的角色统一在方法上使用@PreAuthorize注解校验
# 延伸阅读