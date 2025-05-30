package dao;

import entities.Biglietto;
import entities.Mezzo;
import entities.Periodo;
import entities.Utente;
import enumerated.StatoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class MezzoDAO {
    private EntityManager em;

    public MezzoDAO( EntityManager em){
        this.em=em;
    }

    public List<Periodo> getPeriodoAttività(){
        TypedQuery<Periodo> query=em.createQuery("SELECT p FROM Periodo p WHERE p.statoMezzo= :stato",Periodo.class);
        query.setParameter("stato", StatoMezzo.IN_ATTIVITA);
        return query.getResultList();
    }

    public List<Periodo> getPeriodoManutenzione(){
        TypedQuery<Periodo> query = em.createQuery("SELECT p FROM Periodo p WHERE p.statoMezzo= :stato", Periodo.class);
        query.setParameter("stato",StatoMezzo.IN_MANUTENZIONE );
        return query.getResultList();
    }

    public void vidimaIlBiglietto(Utente utente, Mezzo mezzo){
        TypedQuery<Biglietto> query=em.createQuery("SELECT b FROM Biglietto b WHERE b.utente_id = :utente AND b.vidimato= false", Biglietto.class);
        query.setParameter("utente", utente);
        query.setMaxResults(1);
        List<Biglietto> risultati = query.getResultList();

        if (risultati.isEmpty()){
            System.out.println("Nessun biglietto trovao per l'utente: " + utente );
        }else{
            Biglietto biglietto = risultati.getFirst();
            em.getTransaction().begin();
            biglietto.setVidimato(true);
            biglietto.setDataVidimazione(LocalDate.now());
            biglietto.setMezzo(mezzo);
            em.getTransaction().commit();
            System.out.println("Biglietto vidimato: " + biglietto);
        }
    }

    public List<Biglietto> prendiBigliettiDaMezzo(Mezzo mezzo){
        TypedQuery<Biglietto> query=em.createQuery("SELECT b FROM Biglietto b WHERE b.vidimato=true AND b.mezzo= :mezzo", Biglietto.class);
        query.setParameter("mezzo",mezzo);
        return query.getResultList();
    }

    public List<Biglietto> prendiBigliettiDaData(LocalDate data){
        TypedQuery<Biglietto> query=em.createQuery("SELECT b FROM Biglietto b WHERE b.dataVidimazione= :data AND b.vidimato= true", Biglietto.class);
        query.setParameter("data",data);
        return query.getResultList();
    }
}
