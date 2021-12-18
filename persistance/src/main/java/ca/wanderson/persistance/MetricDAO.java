package ca.wanderson.persistance;

import ca.wanderson.persistance.model.Metric;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * Session bean to manage application data.
 * @author Will Anderson
 */
@Stateful
public class MetricDAO {
    @PersistenceContext(unitName = "logs-database", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    /**
     * Persist a metric.
     * @param metric The metric.
     */
    public void saveMetric(Metric entity) {
        em.persist(entity);
    }

    /**
     * Get all metrics.
     * @return The metrics.
     */
    public List<Metric> getMetrics() {
        return em.createQuery("SELECT m FROM Metric m", Metric.class).getResultList();
    }

    /**
     * Get a metric by ID.
     * @param id The ID.
     * @return The metric.
     */
    public Metric getMetricById(Long id) {
        return em.createQuery("SELECT m FROM Metric m WHERE m.id = :id", Metric.class).setParameter("id", id).getSingleResult();
    }

    /**
     * Get all associated metrics by application ID.
     * @param applicationId The application ID.
     * @return The metrics.
     */
    public List<Metric> getMetricsByApplicationId(Long applicationId) {
        return em.createQuery("SELECT m FROM Metric m WHERE m.application.id = :applicationId", Metric.class)
                .setParameter("applicationId", applicationId).getResultList();
    }

    /**
     * Delete a metric by ID.
     * @param id The ID.
     */
    public void deleteMetric(Long id) {
        Metric entity = em.find(Metric.class, id);
        em.remove(entity);
    }
}
