FROM java:8
COPY *.jar /myblog.jar
CMD ["--server.port=8090"]
EXPOSE 8090
ENTRYPOINT ["java","-jar","/myblog.jar"]