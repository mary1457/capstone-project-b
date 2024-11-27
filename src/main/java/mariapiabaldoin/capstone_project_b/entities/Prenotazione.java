package mariapiabaldoin.capstone_project_b.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prenotazione {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "centro_estetico_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CentroEstetico centroEstetico;


    private LocalDateTime data;


    public Prenotazione(Cliente cliente, CentroEstetico centroEstetico, LocalDateTime data) {
        this.cliente = cliente;
        this.centroEstetico = centroEstetico;
        this.data = data;
    }


}
