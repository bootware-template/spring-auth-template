# Spring Auth Template

## Setup

```sh
./mvnw clean
./mvnw -N install
./mvnw -N -Drun=init-container
./mvnw -f tools/migration -P migrate-test
./mvnw -f tools/entity -P reveng
./mvnw install -f entity
```
