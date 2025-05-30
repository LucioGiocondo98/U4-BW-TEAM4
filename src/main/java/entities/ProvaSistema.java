package entities;

import dao.*;
import enumerated.Ruolo;
import enumerated.TipoMezzo;
import enumerated.Validita;
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
       /* Utente admin = new Utente();
        admin.setNome("Team4");
        admin.setCognome("Epicode");
        admin.setEmail("team4@epicode.com");
        admin.setPassword("team4");
        admin.setRuolo(Ruolo.AMMINISTRATORE);
        utenteDAO.salvaUtente(admin)*/
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
                System.out.println("6. Elimina tutti gli utenti");
                System.out.println("7. Aggiungi titolo di viaggio");
                System.out.println("8. Aggiungi tratta");
                System.out.println("9. Crea Mezzo");

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
                    case 1 -> creaUtente(scanner, utenteDAO, tesseraDao);
                    case 2 -> rinnovaTessera(scanner, utenteDAO);
                    case 3 -> visualizzaTuttiGliUtenti(utenteDAO);
                    case 4 -> verificaAbbonamento(scanner, utenteDAO);
                    case 5 -> running = false;
                    case 6 -> {
                        System.out.print("Sei sicuro di voler eliminare TUTTI gli utenti? (s/n): ");
                        String conferma = scanner.nextLine().trim().toLowerCase();
                        if (conferma.equals("s")) {
                            utenteDAO.eliminaTuttiGliUtenti();
                        } else {
                            System.out.println("Operazione annullata.");
                        }
                    }
                    case 7 -> aggiungiTitoloDiViaggio(scanner, titoloDiViaggioDAO,utenteDAO);
                    case 8 -> aggiungiTratta(scanner, trattaDAO);
                    case 9 -> creaMezzo(scanner, mezzoDAO);

                    default -> System.out.println("Scelta non valida.");
                }
            } else {
                System.out.println("1. Rinnova Tessera");
                System.out.println("2. Visualizza i miei dati");
                System.out.println("3. Verifica validità abbonamento (per tessera)");
                System.out.println("4. Acquista titolo di viaggio");
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
                    case 1 -> rinnovaTessera(scanner, utenteDAO);
                    case 2 -> visualizzaUtente(scanner, utenteDAO);
                    case 3 -> verificaAbbonamento(scanner, utenteDAO);
                    case 4 -> acquistaTitoloDiViaggio(scanner, titoloDiViaggioDAO, utenteDAO, loggedUser);

                    case 5 -> running = false;
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

        Ruolo ruolo = Ruolo.GENERICO;

        Utente nuovoUtente = new Utente(nome, cognome, null, null, ruolo, email, password);
        utenteDAO.creaUtente(nuovoUtente, tesseraDao);
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

    private static void acquistaTitoloDiViaggio(Scanner scanner, TitoloDiViaggioDAO titoloDiViaggioDAO, UtenteDAO utenteDAO, Utente utente) {
        System.out.println("\n*** Acquisto Titolo di Viaggio ***");
        var titoliDisponibili = titoloDiViaggioDAO.getAllTitoli();
        if (titoliDisponibili.isEmpty()) {
            System.out.println("Nessun titolo di viaggio disponibile al momento.");
            return;
        }
        System.out.println("Titoli disponibili:");
        for (int i = 0; i < titoliDisponibili.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, titoliDisponibili.get(i));
        }

        System.out.print("Seleziona il numero del titolo da acquistare: ");
        int scelta;
        try {
            scelta = Integer.parseInt(scanner.nextLine());
            if (scelta < 1 || scelta > titoliDisponibili.size()) {
                System.out.println("Scelta non valida.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Inserisci un numero valido.");
            return;
        }

        TitoloDiViaggio titoloSelezionato = titoliDisponibili.get(scelta - 1);
        boolean successo = utenteDAO.acquistaTitolo(utente, titoloSelezionato);

        if (successo) {
            System.out.println("Acquisto effettuato con successo!");
        } else {
            System.out.println("Errore durante l'acquisto, riprova più tardi.");
        }
    }

    private static void aggiungiTratta(Scanner scanner, TrattaDAO trattaDAO) {
        System.out.println("\n*** Aggiungi Nuova Tratta ***");

        System.out.print("Inserisci zona di partenza: ");
        String zonaPartenza = scanner.nextLine().trim();

        System.out.print("Inserisci capolinea: ");
        String capolinea = scanner.nextLine().trim();

        int tempoPrevisto;
        while (true) {
            System.out.print("Inserisci tempo previsto di percorrenza (in minuti): ");
            try {
                tempoPrevisto = Integer.parseInt(scanner.nextLine());
                if (tempoPrevisto <= 0) {
                    System.out.println("Il tempo deve essere maggiore di 0.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }

        Tratta nuovaTratta = new Tratta();
        nuovaTratta.setZonaPartenza(zonaPartenza);
        nuovaTratta.setCapolinea(capolinea);
        nuovaTratta.setTempoPrevistoMinuti(tempoPrevisto);

        trattaDAO.save(nuovaTratta);
        System.out.println("Tratta aggiunta con successo! ID generato: " + nuovaTratta.getId());
    }

    private static void creaMezzo(Scanner scanner, MezzoDAO mezzoDAO) {
        System.out.println("\n*** Crea Nuovo Mezzo ***");

        System.out.print("Inserisci tipo di mezzo (es. Autobus, Tram): ");
        String tipo = scanner.nextLine().trim();

        TipoMezzo tipoMezzoEnum;
        try {
            tipoMezzoEnum = TipoMezzo.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo mezzo non valido. Usa: AUTOBUS, TRAM");
            return;  // Esci o riprova
        }

        Mezzo nuovoMezzo = new Mezzo();
        nuovoMezzo.setTipomezzo(tipoMezzoEnum);

        mezzoDAO.creaMezzo(nuovoMezzo);

        System.out.println("Mezzo creato con successo con ID: " + nuovoMezzo.getId());
    }

    private static void aggiungiTitoloDiViaggio(Scanner scanner, TitoloDiViaggioDAO titoloDiViaggioDAO, UtenteDAO utenteDAO) {
        System.out.println("\n*** Aggiungi Nuovo Titolo di Viaggio ***");

        System.out.print("Inserisci codice univoco: ");
        String codice = scanner.nextLine().trim();

        if (titoloDiViaggioDAO.trovaTitoloDiViaggioPerId(codice) != null) {
            System.out.println("Errore: codice univoco già presente.");
            return;
        }

        System.out.print("Inserisci data emissione (YYYY-MM-DD): ");
        String dataInput = scanner.nextLine().trim();
        LocalDate dataEmissione;
        try {
            dataEmissione = LocalDate.parse(dataInput);
        } catch (Exception e) {
            System.out.println("Formato data non valido.");
            return;
        }

        System.out.print("Scegli tipo titolo (1 = Biglietto, 2 = Abbonamento): ");
        String scelta = scanner.nextLine().trim();

        TitoloDiViaggio nuovoTitolo = null;

        switch (scelta) {
            case "1":
                System.out.print("Inserisci ID utente per il biglietto: ");
                String idUtente = scanner.nextLine().trim();
                Utente utente = utenteDAO.getById(scanner.nextLong());
                if (utente == null) {
                    System.out.println("Utente non trovato.");
                    return;
                }

                nuovoTitolo = new Biglietto();
                ((Biglietto) nuovoTitolo).setUtente(utente);
                ((Biglietto) nuovoTitolo).setVidimato(false);
                ((Biglietto) nuovoTitolo).setDataVidimazione(null);
                nuovoTitolo.setCodiceUnivoco(codice);
                nuovoTitolo.setDataEmissione(dataEmissione);
                break;

            case "2":  // Abbonamento
                System.out.print("Inserisci validità (esempio: MENSILE, ANNUALE): ");
                String validitaStr = scanner.nextLine().trim().toUpperCase();

                Validita validitaEnum;
                try {
                    validitaEnum = Validita.valueOf(validitaStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("Validità non valida. Usa: MENSILE, ANNUALE, ecc.");
                    return;
                }

                nuovoTitolo = new Abbonamento();
                ((Abbonamento) nuovoTitolo).setValidita(validitaEnum);
                nuovoTitolo.setCodiceUnivoco(codice);
                nuovoTitolo.setDataEmissione(dataEmissione);
                break;

            default:
                System.out.println("Scelta non valida.");
                return;
        }
        titoloDiViaggioDAO.aggiungiTitoloDiViaggio(nuovoTitolo);
        System.out.println("Titolo di viaggio aggiunto con successo!");
    }
}
