FROM java:8
COPY *.jar /task-1.0.0-exec.jar
CMD ["--server.port=8082"]
VOLUME "/home/www/logs/my-task/"
EXPOSE 8082
ENTRYPOINT ["java","-jar","/task-1.0.0-exec.jar"]