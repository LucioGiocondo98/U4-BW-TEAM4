package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;
@Entity
public class Tessera {
    @Id
    @GeneratedValue
    protected Long id;
    @Column(name = "data_di_emissione")
    protected LocalDate dataEmissione;
     @Column(name = "data_di_scadenza")
    protected LocalDate dataScadenza;
    protected boolean attiva;

    public Tessera(long id, LocalDate dataEmissione, boolean attiva) {
        this.id = id;
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataEmissione.plusYears(1);
        this.attiva = attiva;
    }
    public Tessera(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public boolean isAttiva() {
        return attiva;
    }

    public void setAttiva(boolean attiva) {
        this.attiva = attiva;
    }
    public boolean isValida() {
        return attiva && (dataScadenza == null || !LocalDate.now().isAfter(dataScadenza));
    }
    public void rinnova() {
        this.dataEmissione = LocalDate.now();
        this.dataScadenza = this.dataEmissione.plusYears(1);
        this.attiva = true;
    }

}

