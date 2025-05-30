package entities;
import enumerated.Ruolo;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue
    protected Long id;
    protected String nome;
    protected String cognome;
    @OneToOne
    @JoinColumn(name ="tessera_id",referencedColumnName = "id",unique = true)
    protected Tessera numeroTessera;
    @OneToMany(mappedBy = "utente")
    protected List<Biglietto> biglietti;
    @OneToOne
    @JoinColumn(name = "abbonamento_id")
    protected Abbonamento abbonamento;
    @Enumerated(EnumType.STRING)
    protected Ruolo ruolo;
    protected String email;
    protected String password;
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TitoloDiViaggio> titoliDiViaggio= new ArrayList<>();



    public Utente(String nome, String cognome, Tessera numeroTessera, Abbonamento abbonamento, Ruolo ruolo,String email,String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.numeroTessera = numeroTessera;
        this.abbonamento = abbonamento;
        this.ruolo = ruolo;
        this.email= email;
        this.password=password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public List<TitoloDiViaggio> getTitoliDiViaggio() {
        return titoliDiViaggio;
    }

    public void setTitoliDiViaggio(List<TitoloDiViaggio> titoliDiViaggio) {
        this.titoliDiViaggio = titoliDiViaggio;
    }

    // Metodo per aggiungere un titolo
    public void addTitoloDiViaggio(TitoloDiViaggio titolo) {
        this.titoliDiViaggio.add(titolo);
        titolo.setUtente(this);  // importante per mantenere la relazione bidirezionale
    }
    public void removeTitoloDiViaggio(TitoloDiViaggio titolo) {
        titoliDiViaggio.remove(titolo);
        titolo.setUtente(null);
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", numeroTessera=" + numeroTessera +
                ", biglietti=" + biglietti +
                ", abbonamento=" + abbonamento +
                ", ruolo=" + ruolo +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utente)) return false;
        Utente utente = (Utente) o;
        return id != null && id.equals(utente.id);
    }
}

