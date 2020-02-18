###  Introduction

This is an example of an App combining some of the most used tech's actually as

##### Docker
##### SpringBoot
##### Vue.js
##### AWS
##### Mysql


###  Running manually

#### Spring boot service
Car Rent Service is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Gradle](https://spring.io/guides/gs/gradle/). You can build a jar file and run it from the command line:

```
cd car-rent-service
./gradlew clean build
java -jar build/libs/*.jar 
```
If you have a locally database mysql running, you can pass as env variable, just like this
- java -jar build/libs/*.jar --spring.profiles.active=mysql --spring.datasource.url=URL_DATABASE

You can then access car-rent-service here: http://localhost:8090/car-rent-service


#### Web front - VueJS
Car Rent Web is a [VueJS](https://vuejs.org/) . You can run it from the command line:
First, you need to install NodeJs(https://nodejs.org/en/)
Then you can run
```
cd car-rent-web
npm run dev
```
You can then access car-rent-web here: http://localhost:8080/car-rent-service

###  Deploy automatically (docker-compose)
If you have Docker installed, you can run all these services automatically with docker-compose
At the root of the project, run this command.
```
docker-compose up
```
If you need to make any changes to the API or database variables, just change these files.

- To change database url
```
open docker-compose.yml 
change SPRING_DATASOURCE_URL
```
- To change the api url that the front end is consuming
```
open car-rent-web/config/prod.env.js
change URL_API
build from Dockerfile
```

#### Attention
The first time you run docker-compose, there may be a problem if the mysql container takes a little longer to be available, but this issue is bypassed by running the command again as the container will already be loaded.

