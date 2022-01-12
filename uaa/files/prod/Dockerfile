FROM java:8
COPY *.jar /uaa-1.0.0-exec.jar
CMD ["--server.port=8003"]
VOLUME "/home/www/logs/uaa/"
EXPOSE 8003
ENTRYPOINT ["java","-jar","/uaa-1.0.0-exec.jar"]