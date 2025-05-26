package entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

    @Entity
    public class Percorso {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private LocalDateTime orarioPartenza;
        private LocalDateTime orarioArrivo;

        @ManyToOne
        private Mezzo mezzo;

        @ManyToOne
        private Tratta tratta;

        // Getters and Setters
    }

