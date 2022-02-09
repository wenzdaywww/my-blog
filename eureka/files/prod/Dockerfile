FROM java:8
COPY *.jar /eureka-1.0.0-exec.jar
CMD ["--server.port=8000"]
VOLUME "/home/www/logs/eureka/"
EXPOSE 8000
ENTRYPOINT ["java","-jar","/eureka-1.0.0-exec.jar"]