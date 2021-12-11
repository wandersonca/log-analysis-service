package ec.finalproject.repository;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import ec.finalproject.model.Application;

@Stateful
public class ApplicationRepository {
    @PersistenceContext(unitName="primary",type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public void saveApplication(Application entity) {
        em.persist(entity);
    }

    public List<Application> getApplications() {
        return em.createQuery("SELECT a FROM Application a", Application.class).getResultList();
    }
}
