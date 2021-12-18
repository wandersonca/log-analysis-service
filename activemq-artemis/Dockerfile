FROM openjdk:latest

ENV ARTEMIS_HOME=/opt/apache-artemis-2.19.0
ADD apache-artemis-2.19.0-bin.tar.gz /opt/

RUN ${ARTEMIS_HOME}/bin/artemis create --name amq --queues logQueue --user admin --password admin --allow-anonymous /opt/mybroker

ADD broker.xml /opt/mybroker/etc/broker.xml

EXPOSE 5445

ENTRYPOINT [ "/opt/mybroker/bin/artemis", "run" ]