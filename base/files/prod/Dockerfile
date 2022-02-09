FROM java:8
COPY *.jar /base-web-1.0.0-exec.jar
CMD ["--server.port=8080"]
VOLUME "/home/www/logs/my-base/"
VOLUME "/home/www/upload/my-base/"
EXPOSE 8080
ENTRYPOINT ["java","-jar","/base-web-1.0.0-exec.jar"]