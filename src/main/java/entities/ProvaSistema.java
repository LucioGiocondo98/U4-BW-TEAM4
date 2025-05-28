package entities;

import dao.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class ProvaSistema {
    public static void main(String[] args) {
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("postgres");
        EntityManager em= emf.createEntityManager();

        DistributoreAutomaticoDao distributoreAutomaticoDao= new DistributoreAutomaticoDao(em);
        MezzoDAO mezzoDAO= new MezzoDAO(em);
        PercorsoDao percorsoDao= new PercorsoDao(em);
        PeriodoDAO periodoDAO= new PeriodoDAO(em);
        PuntoEmissioneDao puntoEmissioneDao= new PuntoEmissioneDao(em);
        RivenditoreDao rivenditoreDao= new RivenditoreDao(em);
        TesseraDao tesseraDao= new TesseraDao(em);
        TitoloDiViaggioDAO titoloDiViaggioDAO= new TitoloDiViaggioDAO(em);
        TrattaDAO trattaDAO= new TrattaDAO(em);
        UtenteDAO utenteDAO= new UtenteDAO(em);
        Scanner scanner= new Scanner(System.in);

        boolean running= true;
        while (running){
            System.out.println("\n***MENU SISTEMA***");
            System.out.println("1. EMETTI BIGLIETTO(DISTRIBUTORE AUTOMATICO o RIVENDITORE AUTORIZZATO)");
            System.out.println("2. EMETTI ABBONAMENTO(SETTIMANALE o MENSILE");
            System.out.println("3. VEDI BIGLIETTI ED ABBONAMENTI EMESSI");
            System.out.println("4. CONTROLLO VALIDITA ABBONAMENTO");
            System.out.println("5. ");
        }
    }
}
