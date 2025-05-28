package dao;

import entities.Tessera;
import entities.Utente;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class UtenteDAO {
    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }


    public Utente trovaPerNumeroTessera(long idTessera) {
        List<Utente> result = em.createQuery(
                        "SELECT u FROM Utente u WHERE u.numeroTessera.id = :idTessera", Utente.class)
                .setParameter("idTessera", idTessera)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public void rimuoviPerNumeroTessera(long idTessera) {
        try {
            em.getTransaction().begin();
            int deleted = em.createQuery(
                            "DELETE FROM Utente u WHERE u.numeroTessera.id = :idTessera")
                    .setParameter("idTessera", idTessera)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }

    // Controlla se l'abbonamento è valido
    public boolean abbonamentoValidoPerNumeroTessera(long idTessera) {
        Utente utente = trovaPerNumeroTessera(idTessera);
        if (utente == null) return false;

        Tessera tessera = utente.getNumeroTessera();
        return tessera != null && tessera.isValida() && utente.getAbbonamento() != null;
    }

    // Rinnova la tessera aggiornando dataEmissione e dataScadenza
    public void rinnovaTessera(Long idTessera) {
        try {
            Tessera tessera = em.find(Tessera.class, idTessera);
            if (tessera != null) {
                em.getTransaction().begin();
                tessera.setDataEmissione(LocalDate.now());
                tessera.setDataScadenza(LocalDate.now().plusYears(1));
                em.getTransaction().commit();
                System.out.println("Tessera rinnovata con successo.");
            } else {
                System.out.println("Tessera non trovata.");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public Utente getById(Long id) {
        return em.find(Utente.class, id);
    }
}
