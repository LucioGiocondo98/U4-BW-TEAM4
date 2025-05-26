package entities;

import jakarta.persistence.*;
import Enumerated.TipoMezzo;
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

    public Mezzo(){
    }

    public Mezzo(int id, TipoMezzo tipomezzo, int capienza) {
        this.id = id;
        this.tipomezzo = tipomezzo;
        this.capienza = capienza;
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

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    @Override
    public String toString() {
        return "Mezzo{" +
                "id=" + id +
                ", tipomezzo=" + tipomezzo +
                ", capienza=" + capienza +
                '}';
    }
}
