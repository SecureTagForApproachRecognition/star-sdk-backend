# STAR SDK Backend
## Introduction
This repository contains a backend implementation (webservice) written with Spring Boot, that implements the specification of the Decentralized Privacy-Preserving Proximity Tracing system.

## Dependencies
* Spring Boot 2.2.6
* Java 8 (or higher)
* Logback

## Database
For development purposes an hsqldb can be used to run the webservice locally. For production systems we recommend connecting to a PostgreSQL dabatase (cluster if possible).

The backend specification is documented [here](https://securetagforapproachrecognition.github.io/star-sdk-backend/).

## Build
To build you need to install Maven.

```bash
cd star-sdk-backend
mvn install
```
## Run
```bash
java -jar starsdk-ws-*.jar
```
## Dockerfiles
We split the pure SDK implementation from app specific (vendor-specific) implementation. Therefore we use two services, both of which run as docker containers. The dockerfile includes a base jdk image to run the jar. To actually build the docker container, you need to place the generated jar in the bin folder.

```bash
cp star-sdk-backend/starsdk-ws/target/starsdk-ws-1.0.0-SNAPSHOT.jar ws-sdk/ws/bin/starsdk-ws-1.0.0.jar
```

```bash
cd ws-sdk && docker build -t <the-tag-we-use> .
```

```bash
docker run <the-tag-we-use>
 ```

## Documentation
To build the documentation install `rusty-swagger` via `cargo`:
```bash
cargo install rusty-swagger
```

Then you can use the makefile to build the documentation.

```bash
cd documentation && make
```