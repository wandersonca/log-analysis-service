#!/bin/bash

# Wait for database to start
sleep 30

/opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -c standalone-full.xml
