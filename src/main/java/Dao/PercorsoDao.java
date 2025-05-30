package dao;

import entities.Percorso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class PercorsoDao {

    private EntityManager em;

    public PercorsoDao(EntityManager em) {
        this.em = em;
    }

    public void save(Percorso percorso) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(percorso);
            tx.commit();
            System.out.println("Percorso salvato con successo.");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Errore nel salvataggio del percorso:");
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
                System.out.println("Percorso eliminato con successo.");
            } else {
                System.out.println("Percorso con ID " + id + " non trovato.");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Errore durante l'eliminazione del percorso:");
            e.printStackTrace();
        }
    }
    public Double calcolaTempoMedioTrattaPerMezzo(Long idTratta, int idMezzo) {
        TypedQuery<Double> query = em.createQuery(
                "SELECT AVG(EXTRACT(EPOCH FROM (p.orarioArrivo - p.orarioPartenza)) / 60) " +
                        "FROM Percorso p WHERE p.tratta.id = :trattaId AND p.mezzo.id = :mezzoId",
                Double.class
        );
        query.setParameter("trattaId", idTratta);
        query.setParameter("mezzoId", idMezzo);
        return query.getSingleResult();
    }
}
