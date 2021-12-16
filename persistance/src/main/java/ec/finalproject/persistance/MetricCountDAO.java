package ec.finalproject.persistance;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import ec.finalproject.persistance.model.MetricCount;

@Stateful
public class MetricCountDAO {
    @PersistenceContext(unitName="logs-database",type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    public void saveMetricCount(MetricCount entity) {
        em.persist(entity);
    }
}
