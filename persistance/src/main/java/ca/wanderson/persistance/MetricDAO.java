package ca.wanderson.persistance;

import ca.wanderson.persistance.model.Metric;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateful
public class MetricDAO {
    @PersistenceContext(unitName = "logs-database", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    public void saveMetric(Metric entity) {
        em.persist(entity);
    }

    public List<Metric> getMetrics() {
        return em.createQuery("SELECT m FROM Metric m", Metric.class).getResultList();
    }

    public Metric getMetricById(Long id) {
        return em.createQuery("SELECT m FROM Metric m WHERE m.id = :id", Metric.class).setParameter("id", id).getSingleResult();
    }

    public List<Metric> getMetricsByApplicationId(Long applicationId) {
        return em.createQuery("SELECT m FROM Metric m WHERE m.application.id = :applicationId", Metric.class)
                .setParameter("applicationId", applicationId).getResultList();
    }

    public void deleteMetric(Long id) {
        Metric entity = em.find(Metric.class, id);
        em.remove(entity);
    }
}
