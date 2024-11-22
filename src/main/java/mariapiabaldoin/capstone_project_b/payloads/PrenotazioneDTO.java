package mariapiabaldoin.capstone_project_b.payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record PrenotazioneDTO(


        @NotNull(message = "Il trattamento del centro estetico è obbligatoria!")
        UUID centroEsteticoId,


        @NotNull(message = "Il trattamento del centro estetico è obbligatoria!")
        LocalDateTime data


) {
}
