package ca.wanderson.api;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.JMSPasswordCredential;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.jboss.logging.Logger;

@JMSDestinationDefinitions(value = {
        @JMSDestinationDefinition(name = "java:/queue/LogQueue", interfaceName = "javax.jms.Queue", destinationName = "LogQueue")
})

/**
 * API to upload logs to the server.
 * @author Will Anderson
 */
@Path("/")
public class UploadAPI {
    private static final Logger LOGGER = Logger.getLogger(UploadAPI.class);

    @Inject
    @JMSConnectionFactory("java:/RemoteJmsXA")
    @JMSPasswordCredential(userName = "admin", password = "admin")
    private JMSContext context;

    @Resource(lookup = "java:global/remoteContext/logQueue")
    private Queue queue;

    /**
     * Upload multiple log messages to the server.
     * @param appId The application ID.
     * @param input The log messages.
     */
    @POST
    @Path("/application/{appId}/upload/file")
    public void postFile(@PathParam("appId") Long appId, String input) throws Exception {
        LOGGER.info("/upload/file Received for application ID " + appId);
        LOGGER.debug("Logs:\n" + input);
        String[] lines = input.split(System.lineSeparator());
        for (String line : lines) {
            MapMessage mapMessage = context.createMapMessage();
            mapMessage.setLong("appId", appId);
            mapMessage.setString("message", line);
            context.createProducer().send(queue, mapMessage);
        }
    }
    /**
     * Upload a single log message to the server.
     * @param appId The application ID.
     * @param input The log message.
     */
    @POST
    @Path("/application/{appId}/upload/line")
    public void postLine(@PathParam("appId") Long appId, String input) throws Exception {
        LOGGER.info("/upload/line Received for application ID " + appId);
        LOGGER.debug("Log: " + input);
        MapMessage mapMessage = context.createMapMessage();
        mapMessage.setLong("appId", appId);
        mapMessage.setString("message", input);
        context.createProducer().send(queue, mapMessage);
    }
}
