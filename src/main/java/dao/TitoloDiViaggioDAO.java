package dao;

import entities.TitoloDiViaggio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.TimerTask;

public class TitoloDiViaggioDAO {
    private EntityManager em;

    public TitoloDiViaggioDAO(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void aggiungiTitoloDiViaggio(TitoloDiViaggio tv){
        em.getTransaction().begin();
        em.persist(tv);
        em.getTransaction().commit();
    }
    public TitoloDiViaggio trovaTitoloDiViaggioPerId(String codiceUnivoco){
       return em.find(TitoloDiViaggio.class, codiceUnivoco);
    }
    public void eliminaTitoloDiViaggio(String codiceUnivoco){
        TitoloDiViaggio tv = trovaTitoloDiViaggioPerId(codiceUnivoco);
        if(tv != null){
            em.getTransaction().begin();
            em.remove(tv);
            em.getTransaction().commit();
        }
            else {
            System.out.println("nessun Titolo Di Viaggio con codice univoco "+ codiceUnivoco +" è stato trovato");

        }

    }
    public List<Object[]> numeroTitoliPerPuntoInPeriodo(LocalDate inizio, LocalDate fine) {
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT t.puntoEmissione, count(t) from TitoloDiViaggio t where dataEmissione between :inizio and :fine group by t.puntoEmissione",
                Object[].class
        );
        query.setParameter("inizio", inizio);
        query.setParameter("fine", fine);
        return query.getResultList();

    }
    public List<TitoloDiViaggio> getAllTitoli() {
        return em.createQuery("SELECT t FROM TitoloDiViaggio t", TitoloDiViaggio.class).getResultList();
    }

}
