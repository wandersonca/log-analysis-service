FROM wildfly
ADD ./management-console/target/management-console.war /opt/jboss/wildfly/standalone/deployments/management-console.war