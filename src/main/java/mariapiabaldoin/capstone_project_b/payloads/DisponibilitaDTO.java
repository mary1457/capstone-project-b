package mariapiabaldoin.capstone_project_b.payloads;

import jakarta.validation.constraints.NotNull;
import mariapiabaldoin.capstone_project_b.entities.Stato;

import java.time.LocalDateTime;
import java.util.UUID;

public record DisponibilitaDTO(

        @NotNull(message = "The beauty center ID is required!")
        UUID centroEsteticoId,

        @NotNull(message = "The availability date is required!")
        LocalDateTime data,

        @NotNull(message = "The availability status is required!")
        Stato stato

) {
}
