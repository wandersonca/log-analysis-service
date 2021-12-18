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

    MetricCounter() {
        metricCounts = new ConcurrentHashMap<String, MetricCount>();
        metricsByApplicationId = new HashMap<Long, List<Metric>>();
    }

    public List<Metric> getMetricsByApplicationId(long applicationId) {
        return metricsByApplicationId.get(applicationId);
    }

    @PostConstruct
    @Schedule(second = "*/60", minute = "*", hour = "*", persistent = false)
    private void updateCache() {
        LOGGER.debug("Updating local list of metrics and applications from Database...");
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

    @Schedule(second = "*/15", minute = "*", hour = "*", persistent = false)
    private void saveStats() {
        LOGGER.info("Saving stats to the Database...");
        for (MetricCount metricCount : metricCounts.values()) {
            LOGGER.info("Saving metric count for: " + metricCount.getId() + ": " + metricCount.getCount());
            metricCountDAO.saveMetricCount(metricCount);
        }
        metricCounts.clear();
    }

    public void incrementMetric(Metric metric, Date date) {
        LOGGER.debug("Incrementing metric " + metric.getName() + " for date " + date);
        String key = metric.getId() + "-" + date.toString();
        if (metricCounts.containsKey(key)) {
            metricCounts.get(key).setCount(metricCounts.get(key).getCount() + 1);
        } else {
            metricCounts.put(key, new MetricCount(key, date, metric, 1));
        }
    }
}