package ec.finalproject.stats;

import ec.finalproject.persistance.model.Metric;
import java.util.Arrays;
import java.util.Date;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.jboss.logging.Logger;


@MessageDriven(name = "LogQueue", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/LogQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")})
public class LogMDB implements MessageListener {
    private static final Logger LOGGER = Logger.getLogger(LogMDB.class);

    @EJB
    private MetricCounter MetricCounter;

    private Date parseDateInterval(String date, String time) {
        LOGGER.trace("Date: " + date + " Time: " + time);

        int month = Integer.parseInt(date.substring(0, 2));
        int year = Integer.parseInt(date.substring(2, 4)) + 100; // Date starts counting years at 1900? Y2K...
        int day = Integer.parseInt(date.substring(4, 6));

        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(2, 4));
        //int second = Integer.parseInt(time.substring(4, 6));
        int second = 0; // Round to nearest minute
        LOGGER.trace("month: " + month + " year: " + year + " day: " + day + " hour: " + hour + " minute: " + minute + " second: " + second);
        return new Date(year, month, day, hour, minute, second);
    }

    public void onMessage(Message rcvMessage) {
        MapMessage msg = null;
        try {
            if (rcvMessage instanceof MapMessage) {
                msg = (MapMessage) rcvMessage;
                long appId = msg.getLong("appId");
                String[] columns = msg.getString("message").split(" ");
                LOGGER.debug("Received Message appId: " + appId + " message: " + msg.getString("message"));
                for(Metric metric : MetricCounter.getMetricsByApplicationId(appId)) {
                    LOGGER.debug("Metric found: " + metric.getName());
                    if (columns.length >= 4) {
                        String logLevel = columns[3];
                        if (!metric.getLogLevel().equals("ALL") && !logLevel.equals(metric.getLogLevel())) {
                            LOGGER.debug("Log level does not match: " + logLevel + " != " + metric.getLogLevel());
                            continue;
                        }
                        LOGGER.debug("Log level matches: " + logLevel + " == " + metric.getLogLevel());
                        String message = String.join(" ", Arrays.copyOfRange(columns, 5, columns.length));
                        if (!message.matches(metric.getMessageRegex())) {
                            LOGGER.debug("Message does not match: " + metric.getMessageRegex() + " != " + message);
                            continue;
                        }
                        LOGGER.debug("Message matches: " + message + " == " + metric.getMessageRegex());
                        MetricCounter.incrementMetric(metric, parseDateInterval(columns[0], columns[1]));
                    } else {
                        LOGGER.error("Message does not match expected length: " + msg.getString("message"));
                    }
                }
            } else {
                LOGGER.error("Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (Exception e) {
            LOGGER.error("Error processing message: " + msg, e);
        }
    }
}
