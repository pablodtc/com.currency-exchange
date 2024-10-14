FROM openjdk:17
VOLUME /tmp
EXPOSE 8081
ADD target/com.currency-exchange-0.0.1-SNAPSHOT.jar currency-exchange.jar
ENTRYPOINT ["java", "-jar", "/currency-exchange.jar"]