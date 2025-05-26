package entities;


import enumerated.Ruolo;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "utenti")
public class Utente {
    protected String nome;
    protected String cognome;
    @Id
    @Column(name ="numero_di_tessera")
    protected Tessera numeroTessera;
    @OneToMany(mappedBy = "utente")
    protected List<Biglietto> biglietti;
    @OneToOne
    protected Abbonamento abbonamento;
    @Enumerated(EnumType.STRING)
    protected Ruolo ruolo;

    public Utente(String nome, String cognome, Tessera numeroTessera, Abbonamento abbonamento, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.numeroTessera = numeroTessera;
        this.abbonamento = abbonamento;
        this.ruolo = ruolo;
    }
    public Utente(){}

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Tessera getNumeroTessera() {
        return numeroTessera;
    }

    public void setNumeroTessera(Tessera numeroTessera) {
        this.numeroTessera = numeroTessera;
    }

    public List<Biglietto> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(List<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }

    public Abbonamento getAbbonamento() {
        return abbonamento;
    }

    public void setAbbonamento(Abbonamento abbonamento) {
        this.abbonamento = abbonamento;
    }
}
