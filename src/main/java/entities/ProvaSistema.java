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

        Utente loggedUser = null;

        // MENU PRE-LOGIN
        boolean preLoginRunning = true;
        while (preLoginRunning) {
            System.out.println("\n*** MENU PRE-LOGIN ***");
            System.out.println("1. Login");
            System.out.println("2. Crea nuovo utente");
            System.out.println("3. Esci");
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
                case 1 -> {
                    loggedUser = login(scanner, em);
                    if (loggedUser != null) {
                        preLoginRunning = false;
                    } else {
                        System.out.println("Login fallito, riprova.");
                    }
                }
                case 2 -> creaUtente(scanner, em, utenteDAO, tesseraDao);
                case 3 -> {
                    System.out.println("Uscita dal programma.");
                    scanner.close();
                    em.close();
                    emf.close();
                    return;
                }
                default -> System.out.println("Scelta non valida.");
            }
        }

        // MENU PRINCIPALE DOPO LOGIN
        boolean running = true;
        while (running) {
            System.out.println("\n*** MENU SISTEMA ***");
            System.out.println("1. Rinnova Tessera");
            System.out.println("2. Visualizza Utente");
            System.out.println("3. Verifica validità abbonamento (per tessera)");
            System.out.println("4. Esci");
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
                case 1 -> rinnovaTessera(scanner, utenteDAO);
                case 2 -> visualizzaUtente(scanner, utenteDAO);
                case 3 -> verificaAbbonamento(scanner, utenteDAO);
                case 4 -> running = false;
                default -> System.out.println("Scelta non valida.");
            }
        }

        em.close();
        emf.close();
        scanner.close();
        System.out.println("Sistema terminato.");
    }

    private static Utente login(Scanner scanner, EntityManager em) {
        System.out.println("*** LOGIN ***");
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            Utente utente = em.createQuery("SELECT u FROM Utente u WHERE u.email = :email", Utente.class)
                    .setParameter("email", email)
                    .getSingleResult();

            if (utente.getPassword().equals(password)) {  // semplice confronto per ora
                System.out.println("Login effettuato con successo, benvenuto " + utente.getNome() +" " +utente.getId() + "!");
                return utente;
            } else {
                System.out.println("Password errata.");
            }
        } catch (jakarta.persistence.NoResultException e) {
            System.out.println("Email non trovata.");
        } catch (Exception e) {
            System.out.println("Errore durante il login: " + e.getMessage());
        }

        return null;
    }

    private static void creaUtente(Scanner scanner, EntityManager em, UtenteDAO utenteDAO, TesseraDao tesseraDao) {
        String nome;
        while (true) {
            System.out.print("Inserisci nome: ");
            nome = scanner.nextLine();
            if (nome.matches(".*\\d.*")) {
                System.out.println("Il nome non può contenere numeri. Riprova.");
            } else {
                break;
            }
        }

        String cognome;
        while (true) {
            System.out.print("Inserisci cognome: ");
            cognome = scanner.nextLine();
            if (cognome.matches(".*\\d.*")) {
                System.out.println("Il cognome non può contenere numeri. Riprova.");
            } else {
                break;
            }
        }

        String email;
        while (true) {
            System.out.print("Inserisci email: ");
            email = scanner.nextLine().trim();
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
                System.out.println("Email non valida, riprova.");
            } else {
                break;
            }
        }

        System.out.print("Inserisci password: ");
        String password = scanner.nextLine();

        Ruolo ruolo = null;
        while (ruolo == null) {
            System.out.print("Scegli ruolo ( 1= GENERICO ||  2 = ADMIN): ");
            int sceltaRuolo = scanner.nextInt();
            switch (sceltaRuolo) {
                case 1 -> ruolo = Ruolo.GENERICO;
                case 2 -> ruolo = Ruolo.ADMIN;
                default -> System.out.println("Scelta non valida. Inserisci 1 o 2.");
            }
        }

        try {
            em.getTransaction().begin();

            Tessera nuovaTessera = new Tessera(LocalDate.now(), true);
            tesseraDao.save(nuovaTessera);

            Utente nuovoUtente = new Utente(nome, cognome, nuovaTessera, null, ruolo, email, password);
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
