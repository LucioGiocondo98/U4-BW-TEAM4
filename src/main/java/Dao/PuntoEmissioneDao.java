package Dao;

import entities.PuntoEmissione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class PuntoEmissioneDao {

    private EntityManager em;

    public PuntoEmissioneDao(EntityManager em){
        this.em = em;
    }

    public void save(PuntoEmissione p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public PuntoEmissione getById(int id){
        return em.find(PuntoEmissione.class, id);
    }

    public void delete(PuntoEmissione p){
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    public List<PuntoEmissione> findAll(){
        TypedQuery<PuntoEmissione> query = em.createQuery("from PuntoEmissione", PuntoEmissione.class);
        return query.getResultList();
    }


}
