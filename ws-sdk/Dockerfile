# Dockerfile for STARSDK-WS
# http://www.ubique.ch/

FROM openjdk:8-jdk
MAINTAINER Martin Alig <alig@ubique.ch>
MAINTAINER Tobias Bachmann <bachmann@ubique.ch>
MAINTAINER Felix Haller <haller@ubique.ch>


# Install ws
RUN useradd ws

WORKDIR /home/ws/

# Copy configs
COPY ./ws /home/ws

# Create skeleton
RUN mkdir -p /home/ws/bin && \
    mkdir -p /home/ws/archive && \
    mkdir -p /home/ws/log && \
    mkdir -p /home/ws/tmp

# Copy binary
ADD ./ws/bin/starsdk-ws-1.0.0.jar /home/ws/bin/starsdk-ws-1.0.0.jar

RUN chown -R ws:ws /home/ws

# Access to webinterface
EXPOSE 8080

CMD java -jar $JAVA_OPTS -Dlogging.config=/home/ws/conf/starsdk-ws-logback.xml -Dspring.config.location=/home/ws/conf/starsdk-ws.properties /home/ws/bin/starsdk-ws.jar
