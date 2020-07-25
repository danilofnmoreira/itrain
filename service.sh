#!/bin/bash

BASEDIR="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"

run_maven() {

    docker run --rm \
               -it \
               -w /build \
               -v $BASEDIR:/build \
               -v ~/.m2:/root/.m2 \
               maven:3-adoptopenjdk-11-openj9 $@
}

case $1 in
    up-web)
        docker-compose up web.itrain.dev
        ;;
    log-web)
        docker logs -f --tail 1000 web.itrain.dev
        ;;
    migrate)
        docker-compose up flyway.itrain.dev
        ;;
    up-db)
        docker-compose up -d postgres.itrain.dev adminer.itrain.dev
        ;;
    up)
        docker-compose up -d
        ;;
    down)
        docker-compose down
        ;;
    build)
        run_maven mvn clean install -DskipTests --batch-mode
        ;;
    test)
        run_maven mvn clean install --batch-mode
        ;;
    *)
        echo -e "Invalid option"
        ;;
esac
