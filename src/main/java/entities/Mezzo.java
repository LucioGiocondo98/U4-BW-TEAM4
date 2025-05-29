package entities;

import enumerated.TipoMezzo;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Mezzo {
    @Id
    @GeneratedValue( strategy= GenerationType.SEQUENCE,generator = "sequence")
    @SequenceGenerator(name = "sequence",initialValue =1,allocationSize = 1)
    private int id;
    @Enumerated(value = EnumType.STRING)
    private TipoMezzo tipomezzo;
    private int capienza;
    @OneToMany(mappedBy = "mezzo")
    private List<Periodo> periodi=new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "tratta_id")
    private Tratta tratta;
    public Mezzo(){
    }

    public Mezzo( TipoMezzo tipomezzo, int capienza) {
        this.tipomezzo = tipomezzo;
        switch (tipomezzo) {
            case AUTOBUS -> this.capienza = 50;
            case TRAM -> this.capienza = 100;
            default -> throw new IllegalArgumentException("Tipo mezzo non gestito");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoMezzo getTipomezzo() {
        return tipomezzo;
    }

    public void setTipomezzo(TipoMezzo tipomezzo) {
        this.tipomezzo = tipomezzo;
    }

    public int getCapienza() {
        return capienza;
    }

    public List<Periodo> getPeriodi() {
        return periodi;
    }
    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }
    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", tipomezzo=" + tipomezzo +
                ", capienza=" + capienza +
                ", tratta=" + (tratta != null ? tratta.getId() : "null") +
                '}';
    }
}
