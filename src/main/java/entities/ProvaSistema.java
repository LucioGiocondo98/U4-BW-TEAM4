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
        Utente admin = new Utente();
        admin.setNome("Team4");
        admin.setCognome("Epicode");
        admin.setEmail("team4@epicode.com");
        admin.setPassword("team4");
        admin.setRuolo(Ruolo.AMMINISTRATORE);
        utenteDAO.salvaUtente(admin);
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
                    System.out.println("*** LOGIN ***");
                    System.out.print("Email: ");
                    String email = scanner.nextLine().trim();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    loggedUser = utenteDAO.login(email, password);
                    if (loggedUser != null) {
                        System.out.println("Login effettuato con successo, benvenuto " + loggedUser.getNome() + "!");
                        preLoginRunning = false;
                    } else {
                        System.out.println("Login fallito, riprova.");
                    }
                }
                case 2 -> creaUtente(scanner, utenteDAO, tesseraDao);
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

            if (loggedUser.getRuolo() == Ruolo.AMMINISTRATORE) {
                System.out.println("1. Crea nuovo utente");
                System.out.println("2. Rinnova Tessera");
                System.out.println("3. Visualizza tutti gli utenti");
                System.out.println("4. Verifica validità abbonamento (per tessera)");
                System.out.println("5. Esci");
            } else {
                System.out.println("1. Rinnova Tessera");
                System.out.println("2. Visualizza i miei dati");
                System.out.println("3. Verifica validità abbonamento (per tessera)");
                System.out.println("4. Esci");
            }

            System.out.print("Scelta: ");
            String input = scanner.nextLine();
            int scelta;
            try {
                scelta = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Inserire un numero valido.");
                continue;
            }

            if (loggedUser.getRuolo() == Ruolo.AMMINISTRATORE) {
                switch (scelta) {
                    case 1 -> creaUtente(scanner, utenteDAO, tesseraDao);
                    case 2 -> rinnovaTessera(scanner, utenteDAO);
                    case 3 -> visualizzaTuttiGliUtenti(utenteDAO);
                    case 4 -> verificaAbbonamento(scanner, utenteDAO);
                    case 5 -> running = false;
                    default -> System.out.println("Scelta non valida.");
                }
            } else {
                switch (scelta) {
                    case 1 -> rinnovaTessera(scanner, utenteDAO);
                    case 2 -> visualizzaUtente(scanner,utenteDAO);
                    case 3 -> verificaAbbonamento(scanner, utenteDAO);
                    case 4 -> running = false;
                    default -> System.out.println("Scelta non valida.");
                }
            }
        }


        em.close();
        emf.close();
        scanner.close();
        System.out.println("Sistema terminato.");
    }


    private static void creaUtente(Scanner scanner, UtenteDAO utenteDAO, TesseraDao tesseraDao) {
        System.out.print("Inserisci nome: ");
        String nome = scanner.nextLine().trim();

        String cognome;
        while (true) {
            System.out.print("Inserisci cognome: ");
            cognome = scanner.nextLine().trim();
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
            } else if (utenteDAO.emailEsiste(email)) {
                System.out.println("Email già presente nel sistema, scegline un'altra.");
            } else {
                break;
            }
        }

        System.out.print("Inserisci password: ");
        String password = scanner.nextLine();

        Ruolo ruolo = null;
        while (ruolo == null) {
            System.out.print("Scegli ruolo (1 = GENERICO, 2 = AMMINISTRATORE): ");
            String scelta = scanner.nextLine();
            try {
                int sceltaRuolo = Integer.parseInt(scelta);
                switch (sceltaRuolo) {
                    case 1 -> ruolo = Ruolo.GENERICO;
                    case 2 -> ruolo = Ruolo.AMMINISTRATORE;
                    default -> System.out.println("Scelta non valida. Inserisci 1 o 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Inserire un numero valido.");
            }
        }

        Utente nuovoUtente = new Utente();
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setEmail(email);
        nuovoUtente.setPassword(password);
        nuovoUtente.setRuolo(ruolo);

        utenteDAO.creaUtente(nuovoUtente, tesseraDao);
        System.out.println("Utente creato con successo!");
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
    private static void visualizzaTuttiGliUtenti(UtenteDAO utenteDAO) {
        System.out.println("*** Elenco Utenti Registrati ***");
        utenteDAO.getAllUtenti().forEach(System.out::println);
    }
}
