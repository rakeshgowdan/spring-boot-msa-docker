# DockerFile

FROM: To buid Docker image we need a base image.We are dockerizing java based project so base image will be openjdk.

LABEL: Labels are metadata about image or container. They will not affect the functionality of the application.

WORKDIR: To set working directory of a Docker container at any given time.

COPY: To copy specified files to the image. In our case we are copying product microservice jar from target folder to app folder inside container.

EXPOSE: To expose the service on specific port inside the container.

CMD: To execute the command as soon as container is launched.

## steps to execute dockerfile
 1. please go to the path where we have put docker file and run below command
 ```
 docker build -t <Image Name/Image ID> : <version>
 ```
 Once docker image sucessfully built on docker CLI, you will get successful message on command prompt.
 
 2. docker images or docker image ls command can be used to list the images in local repository.
 ```
 docker images
 ```
 3.Run Docker Image on Docker Engine 
 ```
 docker run -d -p 7070:9090 <Image Id>
 ```
 option d : Run container in background and print container Id. Run in detached mode.

 option p : Publish a container’s port(s) to the host. In our case container will be running on 9090 port 
 and external system or host machine can access the application on 7070 port.

 E.g -p <External port > : <internal port>

 4. This command will show the all running containers in docker engine.
 ```
 docker container ls
 ```
 5. To view the logs on running container We can use below command
 ```
 docker logs -f <container Id>
 ```
## Important Note on Swagger_Spring Boot
Spring boot 2.5.8 above has configuration mismatch with Swagger 3.x

```
 <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-ui</artifactId>
        <version>1.6.6</version>
    </dependency>
```
