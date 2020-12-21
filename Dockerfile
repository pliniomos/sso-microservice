FROM anapsix/alpine-java:8

EXPOSE 8091

ARG JAR_FILE
ADD ${JAR_FILE} sso-microservice.jar
RUN bash -c 'touch /sso-microservice.jar'

CMD java -Djava.security.egd=file:/dev/./urandom -jar /sso-microservice.jar
