FROM amazoncorretto:17-alpine3.17 AS build

ARG MAVEN_VERSION=3.9.9
RUN apk add --no-cache curl tar bash \
    && mkdir -p /usr/share/maven \
    && curl -fsSL https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 \
    && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "/root/.m2"

WORKDIR /app

COPY pom.xml /app/pom.xml
RUN mvn dependency:go-offline

COPY . /app
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine3.17

RUN apk add --no-cache supervisor

COPY --from=build /app/target/student-service-0.0.1-SNAPSHOT.jar /app/student-service.jar

COPY config/supervisord.conf /etc/supervisord.conf

EXPOSE 8080

CMD ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]