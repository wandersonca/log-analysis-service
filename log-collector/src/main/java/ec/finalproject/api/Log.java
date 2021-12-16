package ec.finalproject.api;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.MapMessage;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.Queue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jboss.logging.Logger;

@JMSDestinationDefinitions(
    value = {
        @JMSDestinationDefinition(
            name = "java:/queue/LogQueue",
            interfaceName = "javax.jms.Queue",
            destinationName = "LogQueue"
        )
    }
)

@Path("/")
public class Log {
    private static final Logger LOGGER = Logger.getLogger(Log.class);

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/queue/LogQueue")
    private Queue queue;

    @POST
    @Path("/application/{appId}/upload/file")
    public void postFile(@PathParam("appId") Long appId, String input) throws Exception {
        LOGGER.debug("/upload/file Received: " + appId + "\n" + input);
        String[] lines = input.split(System.lineSeparator());
        for(String line : lines) {
            MapMessage mapMessage = context.createMapMessage();
            mapMessage.setLong("appId", appId);
            mapMessage.setString("message", line);
            context.createProducer().send(queue, mapMessage);
        }
    }

    @POST
    @Path("/application/{appId}/upload/line")
    public void postLine(@PathParam("appId") Long appId, String input) throws Exception {
        LOGGER.debug("/upload/line Received: " + appId + "\n" + input);
        MapMessage mapMessage = context.createMapMessage();
        mapMessage.setLong("appId", appId);
        mapMessage.setString("message", input);
        context.createProducer().send(queue, mapMessage);
    }
}
