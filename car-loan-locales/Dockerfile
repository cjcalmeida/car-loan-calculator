FROM adoptopenjdk/openjdk11-openj9:alpine-jre
COPY target/*.jar deployment/app.jar
EXPOSE 8080 9090
ENV JAVA_OPTS="-Xmx128m -XX:MaxPermSize=128m -noverify -Djava.security.egd=file:/dev/./urandom -XX:+AlwaysPreTouch"

RUN /bin/sh -c 'java -Xscmx80M -Xshareclasses:name=app -Xquickstart -jar /deployment/app.jar &' ; sleep 60 ; pkill -9 -f app

CMD java $JAVA_OPTS -Xscmx80M -Xshareclasses:name=app -jar /deployment/app.jar
