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

    // Getters and Setters
}
