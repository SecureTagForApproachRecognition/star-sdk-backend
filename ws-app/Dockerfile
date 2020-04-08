# Dockerfile for STARSDK-WS-APP
# http://www.ubique.ch/

FROM openjdk:8-jdk
MAINTAINER Martin Alig <alig@ubique.ch>
MAINTAINER Tobias Bachmann <bachmann@ubique.ch>
MAINTAINER Felix Haller <haller@ubique.ch>


# Install ws-app
RUN useradd ws-app

WORKDIR /home/ws-app/

# Copy configs
COPY ./ws-app /home/ws-app

# Create skeleton
RUN mkdir -p /home/ws-app/bin && \
    mkdir -p /home/ws-app/archive && \
    mkdir -p /home/ws-app/log && \
    mkdir -p /home/ws-app/tmp

# Copy binary
ADD ./ws-app/bin/starsdk-ws-app-1.0.0.jar /home/ws-app/bin/starsdk-ws-app-1.0.0.jar

RUN chown -R ws-app:ws-app /home/ws-app

# Access to webinterface
EXPOSE 8080

CMD java -jar $JAVA_OPTS -Dlogging.config=/home/ws-app/conf/starsdk-ws-app-logback.xml -Dspring.config.location=/home/ws-app/conf/starsdk-ws-app.properties /home/ws-app/bin/starsdk-ws-app.jar
