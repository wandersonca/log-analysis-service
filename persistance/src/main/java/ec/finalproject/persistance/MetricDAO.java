package ec.finalproject.persistance;

import ec.finalproject.persistance.model.Metric;
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
}
