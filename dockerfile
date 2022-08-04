FROM openjdk:11
LABEL "Developer"="Rakesh N"
LABEL "Practice"=" to deploy Spring boot service with docker"
WORKDIR {basepath}/docker-workspace
COPY target/spring-boot-msa-docker-0.0.1.jar spring-boot-msa-docker-0.0.1.jar
EXPOSE 8081
CMD ["java","-jar","spring-boot-msa-docker-0.0.1.jar"]