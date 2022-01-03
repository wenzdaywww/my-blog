FROM java:8
COPY *.jar /zuul-1.0.0.jar
CMD ["--server.port=8001"]
VOLUME "/home/ap/logs/zuul/"
#设置环境变量用于eureka注册获取真实IP
ENV EUREKA_INSTANCE_IP-ADDRESS=192.168.1.132 SERVER_PORT=8001
EXPOSE 8001
ENTRYPOINT ["java","-jar","/zuul-1.0.0.jar"]