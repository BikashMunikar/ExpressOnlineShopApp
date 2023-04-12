FROM openjdk:8 as builder
WORKDIR extracted
ADD target/*.jar discovery-server.jar
RUN java -Djarmode=layertools -jar discovery-server.jar extract
#this is a test
FROM openjdk:8
WORKDIR application
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]