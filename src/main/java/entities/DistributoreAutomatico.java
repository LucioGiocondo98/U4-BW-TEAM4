package entities;

import enumerated.StatoDistributore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;

import java.util.List;

@Entity
public class DistributoreAutomatico extends PuntoEmissione{

    @Enumerated(EnumType.STRING)
    private StatoDistributore stato;

    public DistributoreAutomatico(StatoDistributore stato) {
        this.stato = stato;
    }

    public DistributoreAutomatico(int id, List<TitoloDiViaggio> titoliEmissione, StatoDistributore stato) {
        super(id, titoliEmissione);
        this.stato = stato;
    }

    public DistributoreAutomatico(){}

    public StatoDistributore getStato() {
        return stato;
    }

    public void setStato(StatoDistributore stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "DistributoreAutomatico{" +
                "stato=" + stato +
                '}';
    }
}
