package mariapiabaldoin.capstone_project_b.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "clienti")
@Getter
@Setter
@ToString
public class Cliente extends Utente {


    public Cliente() {
    }

    public Cliente(String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }


}