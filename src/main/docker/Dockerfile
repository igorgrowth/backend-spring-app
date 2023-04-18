FROM debian:buster-slim
MAINTAINER Tryniak Igor
RUN apt-get update && apt-get install -y wget gnupg2 apt-transport-https ca-certificates gnupg2 libasound2 libgl1-mesa-dev libgtk-3-0

RUN wget -q https://download.bell-sw.com/java/11.0.13+8/bellsoft-jre11.0.13+8-linux-amd64-full.deb
RUN dpkg -i bellsoft-jre11.0.13+8-linux-amd64-full.deb
RUN rm bellsoft-jre11.0.13+8-linux-amd64-full.deb

COPY /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]