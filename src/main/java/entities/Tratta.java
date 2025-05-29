package entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Tratta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zonaPartenza;
    private String capolinea;
    private int tempoPrevistoMinuti;
    @OneToMany(mappedBy = "tratta")
    private List<Mezzo> mezzi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public void setZonaPartenza(String zonaPartenza) {
        this.zonaPartenza = zonaPartenza;
    }

    public int getTempoPrevistoMinuti() {
        return tempoPrevistoMinuti;
    }

    public void setTempoPrevistoMinuti(int tempoPrevistoMinuti) {
        this.tempoPrevistoMinuti = tempoPrevistoMinuti;
    }

    public List<Mezzo> getMezzi() {
        return mezzi;
    }

    public void setMezzi(List<Mezzo> mezzi) {
        this.mezzi = mezzi;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

}


