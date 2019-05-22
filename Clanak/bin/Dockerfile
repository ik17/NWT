FROM openjdk:8-jdk-alpine
ENV APP_HOME /opt

ARG rabbithost

RUN apk --no-cache add netcat-openbsd
COPY target/Clanak-0.0.1-SNAPSHOT.jar $APP_HOME
COPY Clanak-entrypoint.sh $APP_HOME/Clanak-entrypoint.sh

RUN echo "spring.rabbit.host=$rabbithost">>$APP_HOME/application.properties
RUN chmod 775 $APP_HOME/Clanak-entrypoint.sh
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]

CMD ["/opt/Clanak-entrypoint.sh"]
EXPOSE 8089

