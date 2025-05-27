package entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PuntoEmissione {

    @Id
    @GeneratedValue
    private int id;

@OneToMany(mappedBy = "puntoEmissione_id")
    private List<TitoloDiViaggio> titoliEmissione;

    public PuntoEmissione() {
    }

    public PuntoEmissione(int id, List<TitoloDiViaggio> titoliEmissione) {
        this.id = id;
        this.titoliEmissione = titoliEmissione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TitoloDiViaggio> getTitoliEmissione() {
        return titoliEmissione;
    }

    public void setTitoliEmissione(List<TitoloDiViaggio> titoliEmissione) {
        this.titoliEmissione = titoliEmissione;
    }

    @Override
    public String toString() {
        return "PuntoEmissione{" +
                "id=" + id +
                ", titoliEmissione=" + titoliEmissione +
                '}';
    }
}
