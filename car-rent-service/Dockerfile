FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=/build/libs/car-rent-service*.jar
COPY ${JAR_FILE} app.jar
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.6.0/wait /wait
RUN chmod +x /wait
COPY run.sh /run.sh
CMD /wait && /run.sh
EXPOSE 8090
