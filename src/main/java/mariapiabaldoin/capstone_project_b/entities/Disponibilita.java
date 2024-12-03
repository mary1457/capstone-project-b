package mariapiabaldoin.capstone_project_b.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "disponibilita")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Disponibilita {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "centro_estetico_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CentroEstetico centroEstetico;
    
    private LocalDateTime data;
    @Enumerated(EnumType.STRING)
    private Stato stato;

    @OneToOne
    @JoinColumn(name = "prenotazione_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Prenotazione prenotazione;

    public Disponibilita(CentroEstetico centroEstetico, LocalDateTime data, Stato stato, Prenotazione prenotazione) {
        this.centroEstetico = centroEstetico;
        this.data = data;
        this.stato = stato;
        this.prenotazione = prenotazione;
    }
}