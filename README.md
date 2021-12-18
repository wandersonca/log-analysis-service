# log-analysis-service
A proof of concept log analysis tool using Java EE(Wildfly AS, Servlets, JSP, JAXRS), JMS (ActiveMQ Artemis), JPA/Hibernate (MySQL) and Docker. 

This application has a management console to configure new applications to upload logs from, add new metrics to collect data on, and a dashboard UI to view collected data. It also has a Restful API to publish either log files or single log lines.

# How to build
Install Maven and Docker, then run the following shell script:
```sh
.github/workflows/build.sh
```

# How to start
To start the application, simply run:
```sh
docker compose up
```

# How to test
1. Load up the [management console UI](http://localhost/management-console).
2. Create a new application titled `default` with any description.
3. Create a new metric for the application with the log-level set to WARN and regex empty. 
4. Wait 60seconds for metric to get picked up by the log service (or watch logs).
5. Use CURL to post logs using your application ID (should be 1). This file loads 2,000 lines of HDFS logs into the service. 
```sh
curl --silent https://raw.githubusercontent.com/logpai/loghub/master/HDFS/HDFS_2k.log | curl --silent --data-binary "@-"  http://127.0.0.1:8080/log-collector/api/application/1/upload/file
```
6. View logs on dashboard (may take up to 15seconds to post).