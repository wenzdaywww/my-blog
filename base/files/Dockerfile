FROM java:8
COPY *.jar /base-web-1.0.0-exec.jar
CMD ["--server.port=8070"]
VOLUME "/home/ap/logs/my-base/"
#设置环境变量用于eureka注册获取真实IP，如果不配置可在创建容器时使用--network host网络
ENV SERVER_PORT=8070
EXPOSE 8070
ENTRYPOINT ["java","-jar","/base-web-1.0.0-exec.jar"]