package mariapiabaldoin.capstone_project_b.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "centri_estetici")
@Getter
@Setter
@ToString
public class CentroEstetico extends Utente {

    private String nameBeautyCenter;
    private String address;
    private String city;
    private boolean abilitato;
    @Enumerated(EnumType.STRING)
    private Trattamento trattamenti;


    public CentroEstetico(String name, String surname, String email, String password, String nameBeautyCenter, String address, String city, Trattamento trattamenti) {
        super(name, surname, email, password);
        this.nameBeautyCenter = nameBeautyCenter;
        this.address = address;
        this.city = city;
        this.abilitato = false;
        this.trattamenti = trattamenti;
    }

    public CentroEstetico() {
       
    }

}