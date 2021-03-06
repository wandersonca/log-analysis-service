package ca.wanderson.stats;

import ca.wanderson.persistance.model.Metric;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.jboss.ejb3.annotation.ResourceAdapter;
import org.jboss.logging.Logger;

/**
 * Message driven bean to process incoming log messages.
 * @author Will Anderson
 */
@MessageDriven(name = "LogQueue", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:global/remoteContext/logQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "user", propertyValue = "admin"),
        @ActivationConfigProperty(propertyName = "password", propertyValue = "admin"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1") })
@ResourceAdapter(value="remote-artemis")
public class LogMDB implements MessageListener {
    private static final Logger LOGGER = Logger.getLogger(LogMDB.class);

    @EJB
    private MetricCounter MetricCounter;

    /**
     * Parse a log messages date and time to the nearest minute.
     * @param date The date string.
     * @param time The time string.
     * @return The parsed date.
     */
    private Date parseDateInterval(String date, String time) {
        LOGGER.trace("Date: " + date + " Time: " + time);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(date.substring(2, 4)) + 2000); // add 2000 to year Y2K style year
        cal.set(Calendar.MONTH, Integer.parseInt(date.substring(0, 2)) - 1); // month is 0-based
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(4, 6)));
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0, 2)));
        cal.set(Calendar.MINUTE, 0); // Round to the top of the hour
        cal.set(Calendar.SECOND, 0); // Round to the top of the hour
        cal.set(Calendar.MILLISECOND, 0); // Round to the top of the hour
        return cal.getTime();
    }

    /**
     * Process a log message.
     * @param rcvMessage The log message.
     */
    public void onMessage(Message rcvMessage) {
        MapMessage msg = null;
        try {
            if (rcvMessage instanceof MapMessage) {
                msg = (MapMessage) rcvMessage;
                long appId = msg.getLong("appId");
                String[] columns = msg.getString("message").split(" ");
                LOGGER.debug("Received Message appId: " + appId + " message: " + msg.getString("message"));
                for (Metric metric : MetricCounter.getMetricsByApplicationId(appId)) {
                    LOGGER.debug("Processing metric: " + metric.getName());
                    if (columns.length >= 4) {
                        String logLevel = columns[3];
                        if (!metric.getLogLevel().equals("ALL") && !logLevel.equals(metric.getLogLevel())) {
                            LOGGER.debug("Log level does not match: " + logLevel + " != " + metric.getLogLevel());
                            continue;
                        }
                        LOGGER.debug("Log level matches: " + logLevel + " == " + metric.getLogLevel());
                        String message = String.join(" ", Arrays.copyOfRange(columns, 5, columns.length));
                        if (metric.getMessageRegex().length() > 0 && !Pattern.compile(metric.getMessageRegex()).matcher(message).find()) {
                            LOGGER.debug("Message does not match: " + metric.getMessageRegex() + " != " + message);
                            continue;
                        }
                        LOGGER.debug("Message matches: " + message + " == " + metric.getMessageRegex());
                        LOGGER.info("Matched message: " + message);
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
