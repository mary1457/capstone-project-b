package mariapiabaldoin.capstone_project_b.payloads;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CentroEsteticoDeleteDTO(
        @NotNull(message = "The beauty center ID is required")
        UUID centroEsteticoId) {


}
