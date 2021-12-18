#!/bin/sh
set -e

echo "Maven build..."
mvn clean install

echo "Building Docker images..."
docker build -f wildfly/Dockerfile -t wildfly wildfly
docker build -f management-console/Dockerfile -t management-console  management-console
docker build -f activemq-artemis/Dockerfile -t activemq-artemis activemq-artemis
docker build -t log-collector  .