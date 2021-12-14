package ec.finalproject.stats;

import java.util.List;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
//import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import ec.finalproject.persistance.model.Metric;
import ec.finalproject.persistance.model.Application;
import ec.finalproject.persistance.ApplicationRepository;
import ec.finalproject.persistance.MetricRepository;

/**
 * <p>
 * A simple Message Driven Bean that asynchronously receives and processes the messages that are sent to the queue.
 * </p>
 *
 * @author Serge Pagop (spagop@redhat.com)
 */
@MessageDriven(name = "LogQueue", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/LogQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")})
public class LogMDB implements MessageListener {
    /**
     * @see MessageListener#onMessage(Message)
     */

    @EJB
    private LogCounter logCounter;

    @EJB
    private ApplicationRepository applicationRepository;

    @EJB
    private MetricRepository metricRepository;

    private Date parseDate(String[] dateParts) {
        String date = dateParts[0];
        String time = dateParts[1];
        System.out.println("Date: " + date + " Time: " + time);

        int month = Integer.parseInt(date.substring(0, 2));
        int year = Integer.parseInt(date.substring(2, 4)) + 100; // Date starts counting at 1900? Y2K...
        int day = Integer.parseInt(date.substring(4, 6));
        //System.out.println("Year: " + year + " Month: " + month + " Day: " + day);

        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(2, 4));
        int second = Integer.parseInt(time.substring(4, 6));
        System.out.println("month: " + month + " year: " + year + " day: " + day + " hour: " + hour + " minute: " + minute + " second: " + second);
        return new Date(year, month, day, hour, minute, second);
    }

    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        try {
            if (rcvMessage instanceof TextMessage) {
                msg = (TextMessage) rcvMessage;
                //System.out.println("Received Message from queue: " + msg.getText());
                String[] columns = msg.getText().split(" ");
                List<Application> applications = applicationRepository.getApplications();
                System.out.println("Application: " + applications.get(0).getName());
                List<Metric> metrics = metricRepository.getMetrics();
                for(Metric metric : metrics) {
                    if(metric.getApplication().getName().equals(applications.get(0).getName())) {
                        System.out.println("Metric: " + metric.getName());
                    }
                }


                if (columns.length >= 4) {
                    try {
                        Date date = parseDate(columns);
                        System.out.println(date);
                    } catch (Exception e) {
                        System.out.println("Error parsing date: " + e.getMessage());
                    }
                    String logLevel = columns[3];
                    System.out.println("Log level: " + logLevel);
                    logCounter.incrementApplication(0);
                    if (logLevel.equals("INFO")) {
                        logCounter.incrementMetric(0);
                    } else {
                        logCounter.incrementMetric(1);
                    }
                }
                System.out.println("App Count: " + logCounter.getApplicationCount(0));
                System.out.println("Metric Count(0): " + logCounter.getMetricCount(0));
                System.out.println("Metric Count(1): " + logCounter.getMetricCount(1));
            } else {
                System.out.println("Message of wrong type: " + rcvMessage.getClass().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }
    }
}
