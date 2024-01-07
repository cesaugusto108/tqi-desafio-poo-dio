FROM eclipse-temurin:17.0.9_9-jre
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV ACTIVEPROFILES=default
ENV SECRETKEY=secret
ENV SERVERPORT=port
ENV DBHOST=host
ENV DBPORT=port
ENV DBUSERNAME=username
ENV DBPASSWORD=password
ENTRYPOINT ["java", \
            "-DACTIVE_PROFILES=${ACTIVEPROFILES}", \
            "-DSECRET_KEY=${SECRETKEY}", \
            "-DSERVER_PORT=${SERVERPORT}", \
            "-DDB-HOST=${DBHOST}", \
            "-DDB-PORT=${DBPORT}", \
            "-DDB-USERNAME=${DBUSERNAME}", \
            "-DDB-PASSWORD=${DBPASSWORD}", \
            "-jar", \
            "/app.jar"]