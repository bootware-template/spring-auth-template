# Spring Auth Template

## Setup

```sh
./mvnw clean
./mvnw -N install
./mvnw -N -Drun=init-container
./mvnw -f spring-auth-migration -P migrate-test
./mvnw -f spring-auth-entity -P reveng
./mvnw install -f spring-auth-entity
```

## Show DB Schema

```sh
open ./target/schemaspy/index.html
```

## Get started

Jwt

```sh
curl -X POST -H "Content-Type: application/json" -d '{ "loginId": "User1", "password": "password" }' http://localhost:8888/auth/login
```

## Develop Tools

### Code Visualizer

 - [Requires](https://github.com/sitoolkit/sit-cv#required-software)
  
Run Code Visualizer
```sh
./mvnw -f spring-auth-backend sit-cv:run
```
