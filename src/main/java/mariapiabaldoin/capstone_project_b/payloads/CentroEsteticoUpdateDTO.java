package mariapiabaldoin.capstone_project_b.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import mariapiabaldoin.capstone_project_b.entities.Trattamento;

import java.util.UUID;

public record CentroEsteticoUpdateDTO(
        @NotEmpty(message = "The name is required")
        @Size(min = 2, max = 40, message = "The name must be between 2 and 40 characters")
        String nome,

        @NotEmpty(message = "The surname is required")
        @Size(min = 2, max = 40, message = "The surname must be between 2 and 40 characters")
        String cognome,

        @NotEmpty(message = "The email is required")
        @Email(message = "The entered email is not valid")
        String email,

        @NotEmpty(message = "The beauty center name is required")
        @Size(min = 2, max = 40, message = "The beauty center name must be between 2 and 40 characters")
        String nomeCentroEstetico,

        @NotEmpty(message = "The beauty center address is required")
        @Size(min = 2, max = 40, message = "The address must be between 2 and 40 characters")
        String indirizzo,

        @NotEmpty(message = "The beauty center city is required")
        @Size(min = 2, max = 40, message = "The city must be between 2 and 40 characters")
        String citta,

        @NotNull(message = "The beauty center treatment is required")
        Trattamento trattamento,

        @NotNull(message = "The beauty center ID is required")
        UUID centroEsteticoId

) {
}
