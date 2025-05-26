package entities;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Rivenditore extends PuntoEmissione{

    private String nome;

    public Rivenditore(String nome) {
        this.nome = nome;
    }

    public Rivenditore(int id, List<TitoloDiViaggio> titoliEmissione, String nome) {
        super(id, titoliEmissione);
        this.nome = nome;
    }

    public Rivenditore(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Rivenditore{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
