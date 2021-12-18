<%@ include file="header.jsp" %>
    <div class="container mt-3">
        <h3>Management Console</h3>
        <p>Welcome to the management console. This is where you can manage your applications.</p>
        <p>This is a proof of concept log analysis tool using Java EE(Wildfly AS, Servlets, JSP, JAXRS), JMS (ActiveMQ Artemis), JPA/Hibernate (MySQL) and Docker. This application has a management console to configure new applications to upload logs from, add new metrics to collect data on, and a dashboard UI to view collected data. It also has a Restful API to publish either log files or single log lines.</p>
    </div>
<%@ include file="footer.jsp" %>