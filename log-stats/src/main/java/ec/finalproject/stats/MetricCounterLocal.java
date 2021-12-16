package ec.finalproject.stats;

import ec.finalproject.persistance.model.Metric;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MetricCounterLocal {
    public List<Metric> getMetricsByApplicationId(int applicationId);
    public void incrementMetric(Metric metric, Date date);
}
