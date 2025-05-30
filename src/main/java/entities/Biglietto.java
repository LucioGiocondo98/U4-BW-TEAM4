package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity

public class Biglietto extends TitoloDiViaggio{
    private boolean vidimato;
    @Column(name = "data_di_vidimazione")
    private LocalDate dataVidimazione;
    @ManyToOne
    @JoinColumn(name ="utente_id" )
   private Utente utente;
    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;

    public Biglietto(String codiceUnivoco, LocalDate dataEmissione, Utente utente) {
        super(codiceUnivoco, dataEmissione);
        this.vidimato = false;
        this.dataVidimazione = null;
        this.utente = utente;
        this.mezzo = null;
    }

    public Biglietto() {
    }

    public boolean isVidimato() {
        return vidimato;
    }

    public void setVidimato(boolean vidimato) {
        this.vidimato = vidimato;
    }

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "vidimato=" + vidimato +
                ", dataVidimazione=" + dataVidimazione +
                ", utente=" + utente +
                ", mezzo=" + mezzo +
                '}';
    }
}
