# First we unpack the layered JAR in a "builder" container
FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.10_9_openj9-0.24.0 as builder
WORKDIR /cognitive
COPY build/libs/cognitive-test-*.jar cognitive-flexibility-tstest.jar
RUN java -Djarmode=layertools -jar cognitive-flexibility-tstest.jar extract

# Next we build the final container, copying the unpacked JAR content from "builder"
# This produces smaller update layers when the dependencies don't change, only the application code.
FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.10_9_openj9-0.24.0
LABEL vendor="Finnish Institute of Occupational Health" maintainer="zsolt.homorodi@vtt.fi"

ENV TINI_VERSION 0.19.0
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS
ARG DEBIAN_FRONTEND=noninteractive

WORKDIR /cognitive

ENTRYPOINT ["/usr/bin/tini", "--"]
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "org.springframework.boot.loader.JarLauncher"]

RUN addgroup --system ttlapp && adduser --system --ingroup ttlapp ttlapp

# Install / update Tini (simplest init for containers: https://github.com/krallin/tini)
ADD https://github.com/krallin/tini/releases/download/v${TINI_VERSION}/tini_${TINI_VERSION}-amd64.deb /tini/tini-amd64.deb
ADD https://github.com/krallin/tini/releases/download/v${TINI_VERSION}/tini_${TINI_VERSION}-amd64.deb.asc /tini/tini-amd64.deb.asc

RUN apt-get update \
    && apt-get install -y --no-install-recommends gpg dirmngr \
    && set -ex \
    && for server in $(shuf -e ha.pool.sks-keyservers.net \
                            hkp://p80.pool.sks-keyservers.net:80 \
                            keyserver.ubuntu.com \
                            hkp://keyserver.ubuntu.com:80 \
                            pgp.mit.edu) ; do \
        gpg --keyserver "$server" --recv-keys 595E85A6B1B4779EA4DAAEC70B588DFF0527A9B7 && break || : ; \
    done \
    && gpg --verify /tini/tini-amd64.deb.asc \
    && dpkg -i /tini/tini-amd64.deb \
    && rm -rf /var/lib/apt/lists/*

COPY --from=builder cognitive/dependencies/ ./
COPY --from=builder cognitive/spring-boot-loader ./
COPY --from=builder cognitive/snapshot-dependencies/ ./
COPY --from=builder cognitive/application/ ./

# Make app run as non-root
RUN chown -R ttlapp:ttlapp /cognitive/
USER ttlapp
