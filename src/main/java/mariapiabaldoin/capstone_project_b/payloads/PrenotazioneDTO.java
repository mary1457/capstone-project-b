package mariapiabaldoin.capstone_project_b.payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record PrenotazioneDTO(

        @NotNull(message = "The beauty center ID is required")
        UUID centroEsteticoId,

        @NotNull(message = "The treatment date is required")
        LocalDateTime data

) {
}
