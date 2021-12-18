#!/bin/sh
set -e

echo "Maven build..."
mvn clean install

echo "Building Docker images..."
docker build -f wildfly.Dockerfile -t wildfly .
docker build -f management-console.Dockerfile -t management-console  .
docker build -f log-collector.Dockerfile -t log-collector  .