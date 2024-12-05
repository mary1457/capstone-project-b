package mariapiabaldoin.capstone_project_b.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(name = "preferiti")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Preferito {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cliente cliente;
    @OneToOne
    @JoinColumn(name = "centroEstetico_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CentroEstetico centroEstetico;


    public Preferito(Cliente cliente, CentroEstetico centroEstetico) {

        this.cliente = cliente;
        this.centroEstetico = centroEstetico;
    }
}