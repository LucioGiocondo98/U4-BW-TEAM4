package Dao;

import entities.DistributoreAutomatico;
import entities.Rivenditore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class RivenditoreDao {
    private EntityManager em;

    public RivenditoreDao(EntityManager em){
        this.em = em;
    }

    public void save(Rivenditore p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public Rivenditore getById(int id){
        return em.find(Rivenditore.class, id);
    }

    public void delete(Rivenditore p){
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
    }

    public List<Rivenditore> findAll(){
        TypedQuery<Rivenditore> query = em.createQuery("from Rivenditore", Rivenditore.class);
        return query.getResultList();
    }

    public List<Rivenditore> findByNome(String nome) {
        return em.createQuery(
                        "SELECT r FROM Rivenditore r WHERE r.nomeRivenditore LIKE :nome", Rivenditore.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }
}
