package dao;

import entities.Biglietto;
import entities.Mezzo;
import entities.Periodo;
import entities.Utente;
import enumerated.StatoMezzo;
import enumerated.TipoMezzo;
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

    public void vidimaIlBiglietto(Utente utente, Mezzo mezzo,Long idBiglietto){
        Biglietto biglietto = em.find(Biglietto.class, idBiglietto);

        if (biglietto == null || !biglietto.getUtente().equals(utente)) {
            System.out.println("Biglietto non trovato o non appartiene all'utente.");
            return;
        }

        if (biglietto.isVidimato()) {
            System.out.println("Il biglietto è già stato vidimato.");
            return;
        }

        em.getTransaction().begin();
        biglietto.setVidimato(true);
        biglietto.setDataVidimazione(LocalDate.now());
        biglietto.setMezzo(mezzo);
        em.getTransaction().commit();

        System.out.println("Biglietto vidimato correttamente: " + biglietto);
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

    public List<Mezzo> prendiTuttiMezzi(){
        TypedQuery<Mezzo> query=em.createQuery("SELECT m FROM Mezzo m ",Mezzo.class);
        return query.getResultList();
    }

    public void creaMezzo(Mezzo mezzo) {
        em.getTransaction().begin();
        em.persist(mezzo);
        em.getTransaction().commit();
    }
}
