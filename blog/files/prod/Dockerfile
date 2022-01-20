FROM java:8
COPY *.jar /blog-web-1.0.0-exec.jar
CMD ["--server.port=8091"]
VOLUME "/home/www/logs/my-blog/"
VOLUME "/home/www/upload/my-blog/"
EXPOSE 8091
ENTRYPOINT ["java","-jar","/blog-web-1.0.0-exec.jar"]