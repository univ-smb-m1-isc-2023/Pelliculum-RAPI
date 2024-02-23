FROM eclipse-temurin:17-jre-alpine

COPY ./target/pelliculum-rapi-1.0.jar .

EXPOSE 8080

CMD ["sh","-c","java -XX:InitialRAMPercentage=50 -XX:MaxRAMPercentage=70  -XshowSettings $JAVA_OPTS -jar pelliculum-rapi-1.0.jar"]