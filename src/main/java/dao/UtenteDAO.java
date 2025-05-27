package dao;

import entities.Utente;
import jakarta.persistence.EntityManager;

public class UtenteDAO {
    private EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }
    
    public Utente trovaPerNumeroTessera(long idTessera) {
        try {
            return em.createQuery(
                            "SELECT u FROM Utente u WHERE u.numeroTessera.id = :idTessera", Utente.class)
                    .setParameter("idTessera", idTessera)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    public void rimuoviPerNumeroTessera(long idTessera) {
        Utente utente = trovaPerNumeroTessera(idTessera);
        if (utente != null) {
            em.getTransaction().begin();
            em.remove(utente);
            em.getTransaction().commit();
        }
    }


    public boolean verificaValiditàAbbonamento(long idTessera) {
        Utente utente = trovaPerNumeroTessera(idTessera);
        if (utente == null || utente.getAbbonamento() == null) {
            return false;
        }
        return utente.getAbbonamento().getDataFine().isAfter(java.time.LocalDate.now());
    }
}
