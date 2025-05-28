package entities;

import dao.*;
import enumerated.Ruolo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.Scanner;

public class ProvaSistema {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
        EntityManager em = emf.createEntityManager();

        DistributoreAutomaticoDao distributoreAutomaticoDao = new DistributoreAutomaticoDao(em);
        MezzoDAO mezzoDAO = new MezzoDAO(em);
        PercorsoDao percorsoDao = new PercorsoDao(em);
        PeriodoDAO periodoDAO = new PeriodoDAO(em);
        PuntoEmissioneDao puntoEmissioneDao = new PuntoEmissioneDao(em);
        RivenditoreDao rivenditoreDao = new RivenditoreDao(em);
        TesseraDao tesseraDao = new TesseraDao(em);
        TitoloDiViaggioDAO titoloDiViaggioDAO = new TitoloDiViaggioDAO(em);
        TrattaDAO trattaDAO = new TrattaDAO(em);
        UtenteDAO utenteDAO = new UtenteDAO(em);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\n*** MENU SISTEMA ***");
            System.out.println("1. Crea Utente");
            System.out.println("2. Rinnova Tessera");
            System.out.println("3. Visualizza Utente");
            System.out.println("4. Verifica validità abbonamento (per tessera)");
            System.out.println("5. Esci");
            System.out.print("Scelta: ");

            String input = scanner.nextLine();
            int scelta;
            try {
                scelta = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Inserire un numero valido.");
                continue;
            }

            switch (scelta) {
                case 1 -> creaUtente(scanner, em, utenteDAO, tesseraDao);
                case 2 -> rinnovaTessera(scanner, utenteDAO);
                case 3 -> visualizzaUtente(scanner, utenteDAO);
                case 4 -> verificaAbbonamento(scanner, utenteDAO);
                case 5 -> running = false;
                default -> System.out.println("Scelta non valida.");
            }
        }

        em.close();
        emf.close();
        scanner.close();
        System.out.println("Sistema terminato.");
    }

    private static void creaUtente(Scanner scanner, EntityManager em, UtenteDAO utenteDAO, TesseraDao tesseraDao) {
        System.out.print("Inserisci nome: ");
        String nome = scanner.nextLine();

        System.out.print("Inserisci cognome: ");
        String cognome = scanner.nextLine();

        System.out.print("Inserisci ruolo (USER, ADMIN): ");
        String ruoloInput = scanner.nextLine().trim().toUpperCase();
        Ruolo ruolo;
        try {
            ruolo = Ruolo.valueOf(ruoloInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Ruolo non valido. Imposto 'GENERIC' di default.");
            ruolo = Ruolo.GENERICO;
        }

        try {
            em.getTransaction().begin();

            Tessera nuovaTessera = new Tessera(LocalDate.now(), true);
            tesseraDao.save(nuovaTessera);

            Utente nuovoUtente = new Utente(nome, cognome, nuovaTessera, null, ruolo);
            em.persist(nuovoUtente);

            em.getTransaction().commit();

            System.out.println("Utente creato con successo:");
            System.out.println(nuovoUtente);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.out.println("Errore durante la creazione dell'utente: " + e.getMessage());
        }
    }

    private static void rinnovaTessera(Scanner scanner, UtenteDAO utenteDAO) {
        System.out.print("Inserisci ID della tessera da rinnovare: ");
        try {
            Long idTessera = Long.parseLong(scanner.nextLine());
            utenteDAO.rinnovaTessera(idTessera);
        } catch (NumberFormatException e) {
            System.out.println("ID tessera non valido.");
        }
    }

    private static void visualizzaUtente(Scanner scanner, UtenteDAO utenteDAO) {
        System.out.print("Inserisci ID utente da visualizzare: ");
        try {
            Long idUtente = Long.parseLong(scanner.nextLine());
            Utente utente = utenteDAO.getById(idUtente);
            if (utente != null) {
                System.out.println("Dettagli utente:");
                System.out.println(utente);
            } else {
                System.out.println("Utente non trovato.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID utente non valido.");
        }
    }

    private static void verificaAbbonamento(Scanner scanner, UtenteDAO utenteDAO) {
        System.out.print("Inserisci ID tessera per la verifica: ");
        try {
            Long idTessera = Long.parseLong(scanner.nextLine());
            boolean valido = utenteDAO.abbonamentoValidoPerNumeroTessera(idTessera);
            if (valido) {
                System.out.println("L'abbonamento associato a questa tessera è valido.");
            } else {
                System.out.println("Abbonamento non valido o non presente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID tessera non valido.");
        }
    }
        }

