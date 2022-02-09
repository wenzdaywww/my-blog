FROM java:8
COPY *.jar /task-1.0.0-exec.jar
CMD ["--server.port=8092"]
VOLUME "/home/www/logs/my-task/"
EXPOSE 8092
ENTRYPOINT ["java","-jar","/task-1.0.0-exec.jar"]