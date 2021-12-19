package ca.wanderson.stats;

import ca.wanderson.persistance.ApplicationDAO;
import ca.wanderson.persistance.MetricCountDAO;
import ca.wanderson.persistance.MetricDAO;
import ca.wanderson.persistance.model.Application;
import ca.wanderson.persistance.model.Metric;
import ca.wanderson.persistance.model.MetricCount;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import org.jboss.logging.Logger;


/**
 * Singleton bean to process count log messages.
 * @author Will Anderson
 */
@Singleton
public class MetricCounter {
    private static final Logger LOGGER = Logger.getLogger(MetricCounter.class);

    @EJB
    private ApplicationDAO applicationDAO;

    @EJB
    private MetricDAO metricDAO;

    @EJB
    private MetricCountDAO metricCountDAO;

    private Map<Long, List<Metric>> metricsByApplicationId;
    private Map<String, MetricCount> metricCounts;

    /**
     * Initialize the bean.
     */
    MetricCounter() {
        metricCounts = new ConcurrentHashMap<String, MetricCount>();
        metricsByApplicationId = new HashMap<Long, List<Metric>>();
    }

    /**
     * Get the metrics by application ID.
     * @param applicationId The application ID.
     * @return The metrics.
     */
    public List<Metric> getMetricsByApplicationId(long applicationId) {
        return metricsByApplicationId.get(applicationId);
    }


    /**
     * Update the metric and application cached objects.
     * Runs every 60 seconds.
     */
    @PostConstruct
    @Schedule(second = "*/60", minute = "*", hour = "*", persistent = false)
    private void updateCache() {
        LOGGER.info("Updating local list of metrics and applications from Database...");
        metricsByApplicationId.clear();
        for (Application application : applicationDAO.getApplications()) {
            LOGGER.debug("Updating metrics for application " + application.getName());
            metricsByApplicationId.put(application.getId(), new ArrayList<Metric>());
        }
        for (Metric metric : metricDAO.getMetrics()) {
            LOGGER.debug("Updating metric " + metric.getName());
            metricsByApplicationId.get(metric.getApplication().getId()).add(metric);
        }
    }

    /**
     * Save the metric count to the database.
     * Runs every 15 seconds.
     * @throws InterruptedException
     */
    @Schedule(second = "*/15", minute = "*", hour = "*", persistent = false)
    private void saveStats() throws InterruptedException {
        LOGGER.info("Saving stats to the Database...");
        Thread.sleep((long)(Math.random() * 1000));
        for (Map.Entry<String, MetricCount> entry : metricCounts.entrySet()) {
            LOGGER.info("Saving metric count for: " + entry.getValue().getId() + ": " + entry.getValue().getCount());
            try {
                metricCountDAO.saveMetricCount(entry.getValue());
                metricCounts.remove(entry.getKey());
            } catch (Exception e) {
                LOGGER.error("Error saving metric count, will try again next time.");
            }
        }
    }

    /**
     * Increment the metric count for the given metric.
     * @param metric The metric.
     * @param date The date.
     */
    public void incrementMetric(Metric metric, Date date) {
        LOGGER.debug("Incrementing metric " + metric.getName() + " for date " + date);
        String key = metric.getId() + "-" + date.toString();
        if (metricCounts.containsKey(key)) {
            metricCounts.get(key).setCount(metricCounts.get(key).getCount() + 1);
        } else {
            metricCounts.put(key, new MetricCount(key, date, metric, 1));
        }
        LOGGER.info("Running total for key: " + key + ": " + metricCounts.get(key).getCount());
    }
}