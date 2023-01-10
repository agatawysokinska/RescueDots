FROM openjdk:17-jdk
COPY rescuedots.jar /tmp
ENTRYPOINT java -jar /tmp/rescuedots.jar 
