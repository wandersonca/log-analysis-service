package ec.finalproject.api;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.Queue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

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

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/queue/LogQueue")
    private Queue queue;

    @POST
    @Path("/file")
    public String postFile(String input) {
        String[] lines = input.split(System.lineSeparator());
        for(String line : lines) {
            context.createProducer().send(queue, line);
        }
        return "file";
    }

    @POST
    @Path("/line")
    public String postLine(String input) {
        context.createProducer().send(queue, input);
        return "line";
    }
}
