FROM java:8-jre
VOLUME /tmp
ADD target/webhooks.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
