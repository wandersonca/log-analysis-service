package ec.finalproject.stats;

import javax.ejb.Local;

@Local
public interface LogCounterLocal {
    public void incrementApplication(int id);
    public void incrementMetric(int id);
    public int getApplicationCount(int id);
    public int getMetricCount(int id);
}
