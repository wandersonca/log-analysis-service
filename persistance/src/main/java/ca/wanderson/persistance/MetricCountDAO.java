package ca.wanderson.persistance;

import ca.wanderson.persistance.model.Metric;
import ca.wanderson.persistance.model.MetricCount;
import javax.ejb.Stateful;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import org.jboss.logging.Logger;

/**
 * Session bean to manage application data.
 * @author Will Anderson
 */
@Stateful
public class MetricCountDAO {
    private static final Logger LOGGER = Logger.getLogger(MetricCountDAO.class);

    @PersistenceContext(unitName = "logs-database", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;


    /**
     * Get all metric counts.
     * @return The metric counts.
     */
    public List<MetricCount> getMetricCounts() {
        return em.createQuery("SELECT m FROM MetricCount m", MetricCount.class).getResultList();
    }

    /**
     * Get a metric count by ID.
     * @param id The ID.
     * @return The metric count.
     */
    public List<MetricCount> getMetricCountsByMetricId(long metricId) {
        return em.createQuery("SELECT m FROM MetricCount m WHERE m.metric.id = :metricId", MetricCount.class)
                .setParameter("metricId", metricId).getResultList();
    }

    /**
     * Persist a metric count.
     * @param metricCount The metric count.
     */
    public void saveMetricCount(MetricCount entity) {
        MetricCount existing = em.find(MetricCount.class, entity.getId());

        // Get an attached entity
        Metric attachedMetric = em.find(Metric.class, entity.getMetric().getId());
        entity.setMetric(attachedMetric);

        if (existing != null) {
            existing.setCount(entity.getCount() + existing.getCount());
            em.flush();
            LOGGER.debug("Updated existing metric count " + existing.getId() + ": " + existing.getCount());
        } else {
            em.persist(entity);
            LOGGER.debug("Saved new metric count " + entity.getId() + ": " + entity.getCount());
        }
    }

    /**
     * Delete all metric counts.
     */
    public void deleteAllMetricCounts() {
        List<MetricCount> entities = getMetricCounts();
        for (MetricCount entity : entities) {
            em.remove(entity);
        }
    }
}
