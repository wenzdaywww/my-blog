FROM java:8
COPY *.jar /task-1.0.0-exec.jar
CMD ["--server.port=8004"]
VOLUME "/home/ap/logs/my-task/"
EXPOSE 8004
ENTRYPOINT ["java","-jar","/task-1.0.0-exec.jar"]