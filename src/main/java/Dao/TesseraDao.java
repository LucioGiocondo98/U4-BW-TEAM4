package dao;

import entities.Tessera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TesseraDao {
    private EntityManager em;

    public TesseraDao(EntityManager em){
        this.em = em;
    }

    public void save(Tessera p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public Tessera getById(int id){
        return em.find(Tessera.class, id);
    }

    public void delete(Tessera p){
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    public List<Tessera> findAll(){
        TypedQuery<Tessera> query = em.createQuery("from Tessera", Tessera.class);
        return query.getResultList();
    }

    public List<Tessera> findTessereAttive() {
        TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.attiva = true", Tessera.class
        );
        return query.getResultList();
    }
}
