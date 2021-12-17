package ec.finalproject.persistance;

import ec.finalproject.persistance.model.Application;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Stateful
public class ApplicationDAO {
    @PersistenceContext(unitName = "logs-database", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    public void saveApplication(Application entity) {
        em.persist(entity);
    }

    public List<Application> getApplications() {
        return em.createQuery("SELECT a FROM Application a", Application.class).getResultList();
    }

    public Application getApplicationById(Long id) {
        return em.createQuery("SELECT a FROM Application a WHERE a.id = :id", Application.class).setParameter("id", id)
                .getSingleResult();
    }

    public void deleteApplication(Long id) {
        Application entity = em.find(Application.class, id);
        em.remove(entity);
    }
}
