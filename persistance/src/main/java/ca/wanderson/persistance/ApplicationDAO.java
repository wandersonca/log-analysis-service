package ca.wanderson.persistance;

import ca.wanderson.persistance.model.Application;
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
public class ApplicationDAO {
    @PersistenceContext(unitName = "logs-database", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    /**
     * Persist an application.
     * @param application The application.
     */
    public void saveApplication(Application entity) {
        em.persist(entity);
    }

    /**
     * Get all applications.
     * @return The applications.
     */
    public List<Application> getApplications() {
        return em.createQuery("SELECT a FROM Application a", Application.class).getResultList();
    }

    /**
     * Get an application by ID.
     * @param id The ID.
     * @return The application.
     */
    public Application getApplicationById(Long id) {
        return em.createQuery("SELECT a FROM Application a WHERE a.id = :id", Application.class).setParameter("id", id)
                .getSingleResult();
    }

    /**
     * Delete an application by ID.
     * @param id The ID.
     */
    public void deleteApplication(Long id) {
        Application entity = em.find(Application.class, id);
        em.remove(entity);
    }
}
