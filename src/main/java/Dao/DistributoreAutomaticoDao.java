package Dao;

import Enumerated.StatoDistributore;
import entities.DistributoreAutomatico;
import entities.PuntoEmissione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DistributoreAutomaticoDao {

    private EntityManager em;

    public DistributoreAutomaticoDao(EntityManager em){
        this.em = em;
    }

    public void save(DistributoreAutomatico p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public DistributoreAutomatico getById(int id){
        return em.find(DistributoreAutomatico.class, id);
    }

    public void delete(DistributoreAutomatico p){
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    public List<DistributoreAutomatico> findAll(){
        TypedQuery<DistributoreAutomatico> query = em.createQuery("from DistributoreAutomatico", DistributoreAutomatico.class);
        return query.getResultList();
    }

    public List<DistributoreAutomatico> findByStato(StatoDistributore stato) {
        return em.createQuery(
                        "SELECT d FROM DistributoreAutomatico d WHERE d.stato = :stato", DistributoreAutomatico.class)
                .setParameter("stato", stato)
                .getResultList();
    }
}
