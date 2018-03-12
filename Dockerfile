# Dockerfile

FROM debian:stretch
MAINTAINER Markus Sabadello <markus@danubetech.com>

USER root

RUN apt-get -y update

RUN apt-get install -y --no-install-recommends openjdk-8-jdk maven

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
ENV PATH $JAVA_HOME/bin:$PATH

# build dependencies

RUN apt-get install -y --no-install-recommends git
RUN apt-get install -y --no-install-recommends libsodium-dev

RUN cd /opt/ && git clone https://github.com/decentralized-identity/universal-resolver.git
RUN cd /opt/universal-resolver/implementations/java && mvn clean
RUN cd /opt/universal-resolver/implementations/java && mvn install -N -DskipTests
RUN cd /opt/universal-resolver/implementations/java/uni-resolver-core && mvn install -N -DskipTests
RUN cd /opt/universal-resolver/implementations/java/uni-resolver-client && mvn install -N -DskipTests

# build did-auth-relying-party

ADD . /opt/did-auth-relying-party

RUN cd /opt/did-auth-relying-party && mvn clean && mvn package

# clean up

RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# done

EXPOSE 8080

CMD "/opt/did-auth-relying-party/run.sh"
