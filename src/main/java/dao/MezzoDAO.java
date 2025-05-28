package dao;

import entities.Periodo;
import enumerated.StatoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MezzoDAO {
    private EntityManager em;

    public MezzoDAO( EntityManager em){
        this.em=em;
    }

    public List<Periodo> getPeriodoAttività(){
        TypedQuery<Periodo> query=em.createQuery("SELECT p FROM Periodo p WHERE p.statoMezzo= stato",Periodo.class);
        query.setParameter("stato", StatoMezzo.In_Attività);
        return query.getResultList();
    }

    public List<Periodo> getPeriodoManutenzione(){
        TypedQuery<Periodo> query = em.createQuery("SELECT p FROM Periodo p WHERE p.statoMezzo= :stato", Periodo.class);
        query.setParameter("stato",StatoMezzo.In_Manutenzione );
        return query.getResultList();
    }
}
