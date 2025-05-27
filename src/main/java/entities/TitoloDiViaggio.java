package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public  abstract  class TitoloDiViaggio {
    @Id
  private  String codiceUnivoco;
    @Column(name = "data_emissione")
  private LocalDate dataEmissione;
    @ManyToOne
    @JoinColumn(name = "titoliEmissione")
  private PuntoEmissione puntoEmissione;


    public TitoloDiViaggio(String codiceUnivoco, LocalDate dataEmissione) {
        this.codiceUnivoco = codiceUnivoco;
        this.dataEmissione = dataEmissione;
    }

    public TitoloDiViaggio() {
    }

    public String getCodiceUnivoco() {
        return codiceUnivoco;
    }

    public void setCodiceUnivoco(String codiceUnivoco) {
        this.codiceUnivoco = codiceUnivoco;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    @Override
    public String toString() {
        return "TitoloDiViaggio{" +
                "codiceUnivoco='" + codiceUnivoco + '\'' +
                ", dataEmissione=" + dataEmissione +
                '}';
    }
}
