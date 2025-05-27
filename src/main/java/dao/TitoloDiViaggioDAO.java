package dao;

import entities.TitoloDiViaggio;
import jakarta.persistence.EntityManager;

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
}
