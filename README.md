# STAR SDK Backend
## Introduction
This is an implementation of the backend services for the Secure Tag for Approach Recognition (STAR) SDK. The idea of the sdk is, to provide an SDK, which enables an easy way to provide methods for contact tracing. This project was built within 71 hours at the HackZurich Hackathon 2020.

## Build
```bash
mvn install
```
## Run
```bash
java -jar starsdk-ws-*.jar
```
## Dockerfiles
We split the pure SDK implementation from app specific (proprietary) implementation. Therefore we use two services, both of which run as docker containers. The dockerfile includes a base jdk image to run the jar. To actually build the docker container, you need to place the generated jar in the bin folder.