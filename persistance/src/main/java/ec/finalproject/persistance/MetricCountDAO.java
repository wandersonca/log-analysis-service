package ec.finalproject.persistance;

import ec.finalproject.persistance.model.MetricCount;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import org.jboss.logging.Logger;

@Stateful
public class MetricCountDAO {
    private static final Logger LOGGER = Logger.getLogger(MetricCountDAO.class);

    @PersistenceContext(unitName = "logs-database", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    public void saveMetricCount(MetricCount entity) {
        MetricCount existing = em.find(MetricCount.class, entity.getId());
        if (existing != null) {
            existing.setCount(entity.getCount() + existing.getCount());
            em.persist(existing);
            LOGGER.debug("Updated existing metric count " + existing.getId() + ": " + existing.getCount());
        } else {
            em.persist(entity);
            LOGGER.debug("Saved new metric count " + entity.getId() + ": " + entity.getCount());
        }
    }
}
