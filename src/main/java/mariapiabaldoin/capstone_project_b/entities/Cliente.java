package mariapiabaldoin.capstone_project_b.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "clienti")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Cliente extends Utente {


    public Cliente(String nome, String cognome, String email, String password) {
        super(nome, cognome, email, password);
    }
}