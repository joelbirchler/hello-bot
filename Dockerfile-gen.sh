#!/usr/bin/env bash

if [[ $(uname -m) == arm* ]]; then
  BASE_IMAGE=rpi-lein:latest
else
  BASE_IMAGE=clojure:lein-2.7.1-alpine
fi

cat  << EOF
FROM $BASE_IMAGE
MAINTAINER Joel Birchler <joel@joelbirchler.com>

RUN mkdir /app
WORKDIR /app

# Dependencies
COPY project.clj /app
RUN lein deps

# Standalone uberjar
COPY . /app
RUN lein uberjar

ENV YELLOW_LED=22 \
    GREEN_LED=23 \
    LEFT_FORWARD_MOTOR=25 \
    LEFT_REVERSE_MOTOR=4 \
    RIGHT_FORWARD_MOTOR=17 \
    RIGHT_REVERSE_MOTOR=18

CMD ["java", "-jar", "/app/target/uberjar/hello-bot-0.2.0-SNAPSHOT-standalone.jar"]
EOF
