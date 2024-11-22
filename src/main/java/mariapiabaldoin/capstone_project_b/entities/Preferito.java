package mariapiabaldoin.capstone_project_b.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "preferiti")
@Getter
@Setter
@ToString
public class Preferito {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nameBeautyCenter;
    private String address;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToOne
    @JoinColumn(name = "centroEstetico_id")
    private CentroEstetico centroEstetico;


    public Preferito() {
    }

    public Preferito(String nameBeautyCenter, String address, Cliente cliente, CentroEstetico centroEstetico) {

        this.nameBeautyCenter = nameBeautyCenter;
        this.address = address;
        this.cliente = cliente;
        this.centroEstetico = centroEstetico;
    }
}