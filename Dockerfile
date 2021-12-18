FROM wildfly
ADD ./log-collector/target/log-collector.war /opt/jboss/wildfly/standalone/deployments/log-collector.war
ADD ./log-stats/target/log-stats.war /opt/jboss/wildfly/standalone/deployments/log-stats.war