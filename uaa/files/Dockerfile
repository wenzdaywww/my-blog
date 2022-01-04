FROM java:8
COPY *.jar /uaa-1.0.0-exec.jar
CMD ["--server.port=8003"]
VOLUME "/home/ap/logs/uaa/"
#设置环境变量用于eureka注册获取真实IP
#ENV EUREKA_INSTANCE_IP-ADDRESS=192.168.1.131 SERVER_PORT=8003
EXPOSE 8003
ENTRYPOINT ["java","-jar","/uaa-1.0.0-exec.jar"]