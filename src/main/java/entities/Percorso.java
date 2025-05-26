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

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public LocalDateTime getOrarioPartenza() {
            return orarioPartenza;
        }

        public void setOrarioPartenza(LocalDateTime orarioPartenza) {
            this.orarioPartenza = orarioPartenza;
        }

        public LocalDateTime getOrarioArrivo() {
            return orarioArrivo;
        }

        public void setOrarioArrivo(LocalDateTime orarioArrivo) {
            this.orarioArrivo = orarioArrivo;
        }

        public Mezzo getMezzo() {
            return mezzo;
        }

        public void setMezzo(Mezzo mezzo) {
            this.mezzo = mezzo;
        }

        public Tratta getTratta() {
            return tratta;
        }

        public void setTratta(Tratta tratta) {
            this.tratta = tratta;
        }
    }

