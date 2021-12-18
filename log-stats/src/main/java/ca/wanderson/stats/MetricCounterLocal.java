package ca.wanderson.stats;

import ca.wanderson.persistance.model.Metric;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 * Singleton bean to process count log messages.
 * @author Will Anderson
 */
@Local
public interface MetricCounterLocal {
    /**
     * Get the metrics by application ID.
     * @param applicationId The application ID.
     * @return The metrics.
     */
    public List<Metric> getMetricsByApplicationId(int applicationId);

    /**
     * Increment the metric count for the given metric.
     * @param metric The metric.
     * @param date The date.
     */
    public void incrementMetric(Metric metric, Date date);
}
