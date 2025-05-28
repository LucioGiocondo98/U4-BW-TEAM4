package entities;

import enumerated.Validita;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
@Entity
public class Abbonamento extends  TitoloDiViaggio {
    @Enumerated(EnumType.STRING)
    private Validita validita;

    public Abbonamento(String codiceUnivoco, LocalDate dataEmissione, Validita validita) {
        super(codiceUnivoco, dataEmissione);
        this.validita = validita;
    }

    public Abbonamento() {
    }

    public Abbonamento(String codiceUnivoco, LocalDate dataEmissione) {
        super(codiceUnivoco, dataEmissione);
    }

    public Validita getValidita() {
        return validita;
    }

    public void setValidita(Validita validita) {
        this.validita = validita;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "validita=" + validita +
                '}';
    }
}
