FROM openjdk:8-jdk-alpine
ENV APP_HOME /opt

RUN apk --no-cache add netcat-openbsd
COPY target/Zuul-0.0.1-SNAPSHOT.jar $APP_HOME
COPY Zuul-entrypoint.sh $APP_HOME/Zuul-entrypoint.sh
RUN chmod 775 $APP_HOME/Zuul-entrypoint.sh
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]

CMD ["/opt/Zuul-entrypoint.sh"]
EXPOSE 8084

