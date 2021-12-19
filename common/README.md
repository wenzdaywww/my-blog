# 项目介绍
## 公共方法包
### 1、要使用其中的配置或者服务，需要在启动类添加包扫描路径，
###如需要使用config包中配置@ComponentScan(basePackages = {"com.www.myblog.common.config","项目扫描包路径"})
### 2、application.yml需要配置的参数
#### file.imgUrlPath 配置访问图片的相对路径
#### file.imgSavePath #图片保存的绝对路径，window的路径必须是\且路径最后也需要\
#### file.otherUrlPath #配置访问图片外其他文件的相对路径
#### file.otherSavePath #图片外其他文件保存的绝对路径，window的路径必须是\且路径最后也需要\

# 延伸阅读