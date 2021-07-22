FROM java:8
COPY *.jar /myblog.jar
CMD ["--server.port=8080"]
EXPOSE 8080
ENTRYPOINT ["java","-jar","/myblog.jar"]