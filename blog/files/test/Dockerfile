FROM java:8
COPY *.jar /blog-web-1.0.0-exec.jar
CMD ["--server.port=8080"]
VOLUME "/home/ap/logs/my-blog/"
VOLUME "/home/ap/upload/my-blog/"
EXPOSE 8080
ENTRYPOINT ["java","-jar","/blog-web-1.0.0-exec.jar"]