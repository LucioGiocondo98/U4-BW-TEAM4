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

    public Biglietto(String codiceUnivoco, LocalDate dataEmissione, boolean vidimato, LocalDate dataVidimazione) {
        super(codiceUnivoco, dataEmissione);
        this.vidimato = vidimato;
        this.dataVidimazione = dataVidimazione;
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

    @Override
    public String toString() {
        return "Biglietto{" +
                "vidimato=" + vidimato +
                ", dataVidimazione=" + dataVidimazione +
                '}';
    }
}
