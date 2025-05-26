package entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import Enumerated.StatoMezzo;

@Entity
@Table
public class Periodo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sequence1")
    @SequenceGenerator(name="sequence1" ,initialValue =1,allocationSize = 1 )
    private int id;
    @ManyToOne
    @JoinColumn(name="mezzo_id")
    private int mezzo;
    @Enumerated(value = EnumType.STRING)
    private StatoMezzo statoMezzo;
    private LocalDate inizioPeriodo;
    private LocalDate finePeriodo;

    public Periodo(){
    }

    public Periodo(int id, int mezzo, StatoMezzo statoMezzo, LocalDate inizioPeriodo, LocalDate finePeriodo) {
        this.id = id;
        this.mezzo = mezzo;
        this.statoMezzo = statoMezzo;
        this.inizioPeriodo = inizioPeriodo;
        this.finePeriodo = finePeriodo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMezzo() {
        return mezzo;
    }

    public void setMezzo(int mezzo) {
        this.mezzo = mezzo;
    }

    public StatoMezzo getStatoMezzo() {
        return statoMezzo;
    }

    public void setStatoMezzo(StatoMezzo statoMezzo) {
        this.statoMezzo = statoMezzo;
    }

    public LocalDate getInizioPeriodo() {
        return inizioPeriodo;
    }

    public void setInizioPeriodo(LocalDate inizioPeriodo) {
        this.inizioPeriodo = inizioPeriodo;
    }

    public LocalDate getFinePeriodo() {
        return finePeriodo;
    }

    public void setFinePeriodo(LocalDate finePeriodo) {
        this.finePeriodo = finePeriodo;
    }

    @Override
    public String toString() {
        return "Periodo{" +
                "id=" + id +
                ", mezzo=" + mezzo +
                ", statoMezzo=" + statoMezzo +
                ", inizioPeriodo=" + inizioPeriodo +
                ", finePeriodo=" + finePeriodo +
                '}';
    }
}