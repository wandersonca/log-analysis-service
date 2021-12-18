FROM jboss/wildfly:18.0.1.Final

ADD startup.sh /opt/jboss/wildfly/bin/startup.sh
ADD standalone-full.xml /opt/jboss/wildfly/standalone/configuration/standalone-full.xml
ADD main /opt/jboss/wildfly/modules/system/layers/base/com/mysql/main
RUN /opt/jboss/wildfly/bin/add-user.sh -u admin -p admin -g admin

ENTRYPOINT [ "/opt/jboss/wildfly/bin/startup.sh" ]