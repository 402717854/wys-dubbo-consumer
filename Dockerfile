FROM registry2.cosmoplat.com/cosmoplat-bj-test/java8-apm:1.1.0

VOLUME /var/applogs

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone
VOLUME /tmp

ADD target/wys-dubbor-consumer-1.0-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080