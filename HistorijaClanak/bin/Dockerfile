FROM openjdk:8-jdk-alpine
ENV APP_HOME /opt

ARG rabbithost

RUN apk --no-cache add netcat-openbsd
COPY target/HistorijaClanak-0.0.1-SNAPSHOT.jar $APP_HOME
COPY HistorijaClanak-entrypoint.sh $APP_HOME/HistorijaClanak-entrypoint.sh

RUN echo "spring.rabbit.host=$rabbithost">>$APP_HOME/application.properties
RUN chmod 775 $APP_HOME/HistorijaClanak-entrypoint.sh
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]

CMD ["/opt/HistorijaClanak-entrypoint.sh"]
EXPOSE 8090

