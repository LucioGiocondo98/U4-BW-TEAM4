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
        LocalDate oggi = LocalDate.now();
        LocalDate scadenzaNuova = oggi.plusYears(1);

        try {
            em.getTransaction().begin();
            int updated = em.createQuery(
                            "UPDATE Tessera t SET t.dataEmissione = :oggi, t.dataScadenza = :scadenzaNuova WHERE t.id = :idTessera")
                    .setParameter("oggi", oggi)
                    .setParameter("scadenzaNuova", scadenzaNuova)
                    .setParameter("idTessera", idTessera)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        }
    }
}
