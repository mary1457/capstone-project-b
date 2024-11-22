package mariapiabaldoin.capstone_project_b.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@ToString

public class Prenotazione {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "centro_estetico_id")
    private CentroEstetico centroEstetico;


    private LocalDateTime data;


    public Prenotazione() {

    }

    public Prenotazione(Cliente cliente, CentroEstetico centroEstetico, LocalDateTime data) {
        this.cliente = cliente;
        this.centroEstetico = centroEstetico;
        this.data = data;
    }
}
