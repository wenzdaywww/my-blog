FROM java:8
COPY *.jar /base-web-1.0.0-exec.jar
CMD ["--server.port=8070"]
VOLUME "/home/www/logs/my-base/"
VOLUME "/home/www/upload/my-base/"
EXPOSE 8090
ENTRYPOINT ["java","-jar","/base-web-1.0.0-exec.jar"]