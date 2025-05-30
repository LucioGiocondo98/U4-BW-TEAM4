package dao;

import entities.Biglietto;
import entities.Tessera;
import entities.TitoloDiViaggio;
import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class UtenteDAO {
    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void salvaUtente(Utente utente) {
        em.getTransaction().begin();
        em.persist(utente);
        em.getTransaction().commit();
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


    public void creaUtente(Utente utente, TesseraDao tesseraDao) {
        try {
            em.getTransaction().begin();

            Tessera tessera = new Tessera(LocalDate.now(), true);
            tesseraDao.save(tessera);
            utente.setNumeroTessera(tessera);

            em.persist(utente);
            em.getTransaction().commit();

            System.out.println("Utente creato con successo:");
            System.out.println(utente);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.out.println("Errore durante la creazione dell'utente: " + e.getMessage());
        }
    }
    public Utente login(String email, String password) {
        try {
            Utente utente = em.createQuery("SELECT u FROM Utente u WHERE u.email = :email", Utente.class)
                    .setParameter("email", email)
                    .getSingleResult();
            if (utente.getPassword().equals(password)) {
                return utente;
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
    }
    public boolean emailEsiste(String email) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM Utente u WHERE u.email = :email", Long.class);
        query.setParameter("email", email);
        return query.getSingleResult() > 0;
    }
    public List<Utente> getAllUtenti() {
        return em.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
    }
        public void eliminaUtente(Long id) {
            Utente utente = getById(id);
            if (utente != null) {
                try {
                    em.getTransaction().begin();
                    em.remove(utente);
                    em.getTransaction().commit();
                } catch (Exception e) {
                    em.getTransaction().rollback();
                    e.printStackTrace();
                }
            } else {
                System.out.println("Utente con id " + id + " non trovato.");
            }
        }
    public void eliminaTuttiGliUtenti() {
        try {
            em.getTransaction().begin();
            int deletedCount = em.createQuery("DELETE FROM Utente").executeUpdate();
            em.getTransaction().commit();
            System.out.println("Eliminati " + deletedCount + " utenti.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Errore durante l'eliminazione degli utenti.");
            e.printStackTrace();
        }
    }
    public boolean acquistaTitolo(Utente utente, TitoloDiViaggio titolo) {
        try {
            em.getTransaction().begin();

            titolo.setUtente(utente);

            List<Biglietto> biglietti = utente.getBiglietti();
            if (biglietti != null && titolo instanceof Biglietto) {
                biglietti.add((Biglietto) titolo);
                utente.setBiglietti(biglietti);
            }

            if (em.contains(titolo)) {
                em.merge(titolo);
            } else {
                em.persist(titolo);
            }

            em.merge(utente);

            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }
    public List<Biglietto>getBigliettiNonVidimati(Utente utente){
        TypedQuery<Biglietto> query=em.createQuery("SELECT b FROM Biglietto b WHERE b.vidimato= false AND b.utente= :utente", Biglietto.class);
        query.setParameter("utente",utente);
        return query.getResultList();
    }


}

