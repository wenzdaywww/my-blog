FROM java:8
COPY *.jar /zuul-1.0.0-exec.jar
CMD ["--server.port=8001"]
VOLUME "/home/ap/logs/zuul/"
EXPOSE 8001
ENTRYPOINT ["java","-jar","/zuul-1.0.0-exec.jar"]