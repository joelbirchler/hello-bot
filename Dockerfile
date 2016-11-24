FROM clojure:lein-2.7.1-alpine
MAINTAINER Joel Birchler <joel@joelbirchler.com>

RUN mkdir /app
WORKDIR /app

# Dependencies
COPY project.clj /app
RUN lein deps

# Standalone uberjar
COPY . /app
RUN lein uberjar

ENV YELLOW_LED 22
ENV GREEN_LED  23
ENV LEFT_FORWARD_MOTOR  25
ENV LEFT_REVERSE_MOTOR  4
ENV RIGHT_FORWARD_MOTOR 17
ENV RIGHT_REVERSE_MOTOR 18

CMD ["java", "-jar", "/app/target/uberjar/hello-bot-0.2.0-SNAPSHOT-standalone.jar"]
