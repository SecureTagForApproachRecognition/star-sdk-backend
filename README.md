# STAR SDK Backend

> #### Moved to DP-3T!
> 
> As of May 2020, all of our efforts are transitioning to [DP-3T](https://github.com/DP-3T).
> 

![Java CI with Maven](https://github.com/SecureTagForApproachRecognition/star-sdk-backend/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)
## Introduction
This repository contains a backend implementation (webservice) written with Spring Boot, that implements the specification of the Decentralized Privacy-Preserving Proximity Tracing system.

## Dependencies
* Spring Boot 2.2.6
* Java 8 (or higher)
* Logback

## Database
For development purposes an hsqldb can be used to run the webservice locally. For production systems we recommend connecting to a PostgreSQL dabatase (cluster if possible).

The backend API documentation is [here](https://securetagforapproachrecognition.github.io/star-sdk-backend/).

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
The dockerfile includes a base jdk image to run the jar. To actually build the docker container, you need to place the generated jar in the bin folder.

```bash
cp dpppt-sdk-backend/dpppt-sdk-backend-ws/target/dpppt-sdk-backends-1.0.0-SNAPSHOT.jar ws-sdk/ws/bin/dpppt-sdk-backend-ws-1.0.0.jar
```

```bash
cd ws-sdk && docker build -t <the-tag-we-use> .
```

```bash
docker run <the-tag-we-use>
 ```

## API Documentation
To build the API documentation install `rusty-swagger` via `cargo`:
```bash
cargo install rusty-swagger
```

Furthermore we need a tex-distribution. Since we use the `Lato` font, we also need the possibility to generate the fonts for latex. Check your distribution for help.

Then you can use the makefile to build the documentation. The makefile will first build the backend, then generate the tex file and in the end launch a tex-distribution to compile the tex file to a pdf.

```bash
cd documentation && make
```
