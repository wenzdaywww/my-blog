FROM java:8
COPY *.jar /base-web-1.0.0-exec.jar
CMD ["--server.port=8070"]
VOLUME "/home/ap/logs/my-base/"
VOLUME "/home/ap/upload/my-base/"
EXPOSE 8070
ENTRYPOINT ["java","-jar","/base-web-1.0.0-exec.jar"]