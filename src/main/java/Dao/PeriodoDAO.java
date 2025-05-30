package dao;

import entities.Periodo;
import jakarta.persistence.EntityManager;

public class PeriodoDAO {

    private EntityManager em;

    public PeriodoDAO( EntityManager em){
        this.em=em;
    }

    public void aggiungiPeriodo(Periodo periodo){
        em.getTransaction().begin();
        em.persist(periodo);
        em.getTransaction().commit();
    }

    public Periodo findPeriodo(int id){
        return em.find(Periodo.class,id);
    }

    public void eliminaPeriodo(int id){
        Periodo p= findPeriodo(id);
        if(p!=null){
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
        }else{
            System.out.println("Periodo non trovato");
        }
    }

}
