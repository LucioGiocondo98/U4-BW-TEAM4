package entities;

import entities.Percorso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PercorsoDAO {

    private EntityManager em;

    public PercorsoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Percorso percorso) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(percorso);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    public Percorso findById(Long id) {
        return em.find(Percorso.class, id);
    }

    public void delete(Long id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Percorso percorso = em.find(Percorso.class, id);
            if (percorso != null) {
                em.remove(percorso);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }
}
