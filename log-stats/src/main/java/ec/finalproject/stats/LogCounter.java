package ec.finalproject.stats;

import java.util.Map;
import java.util.HashMap;
import javax.ejb.Singleton;


@Singleton
public class LogCounter {
    private Map<Integer, Integer> applicationCounter;
    private Map<Integer, Integer> metricCounter;

    LogCounter() {
        applicationCounter = new HashMap<>();
        metricCounter = new HashMap<>();
    }

    public void incrementApplication(int id) {
        applicationCounter.put(id, getApplicationCount(id) + 1);
    }

    public void incrementMetric(int id) {
        metricCounter.put(id, getMetricCount(id) + 1);
    }

    public int getApplicationCount(int id) {
        return applicationCounter.containsKey(0) ? applicationCounter.get(id) : 0;
    }

    public int getMetricCount(int id) {
        return metricCounter.containsKey(0) ? metricCounter.get(id) : 0;
    }
}