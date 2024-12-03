package mariapiabaldoin.capstone_project_b.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "centri_estetici")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CentroEstetico extends Utente {

    private String nomeCentroEstetico;
    private String indirizzo;
    private String citta;
    private boolean abilitato;
    @Enumerated(EnumType.STRING)
    private Trattamento trattamento;


    @OneToMany(mappedBy = "centroEstetico")
    @ToString.Exclude
    private List<Disponibilita> disponibilita;


    public CentroEstetico(String nome, String cognome, String email, String password, String nomeCentroEstetico, String indirizzo, String citta, Trattamento trattamento) {
        super(nome, cognome, email, password);
        this.nomeCentroEstetico = nomeCentroEstetico;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.abilitato = false;
        this.trattamento = trattamento;
    }


}