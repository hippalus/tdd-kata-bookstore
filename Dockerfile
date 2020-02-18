FROM java:8

ARG JAR_FILE

ADD  target/${JAR_FILE} target/tdd-kata-bookstore-1.0.jar

EXPOSE 8080

ENV JAVA_OPTS=""

ENTRYPOINT java $JAVA_OPTS -jar target/tdd-kata-bookstore-1.0.jar