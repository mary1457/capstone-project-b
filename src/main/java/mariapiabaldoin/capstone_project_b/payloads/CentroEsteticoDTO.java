package mariapiabaldoin.capstone_project_b.payloads;

import jakarta.validation.constraints.*;
import mariapiabaldoin.capstone_project_b.entities.Trattamento;

public record CentroEsteticoDTO(

        @NotEmpty(message = "The name is required!")
        @Size(min = 2, max = 40, message = "The name must be between 2 and 40 characters!")
        String nome,

        @NotEmpty(message = "The surname is required!")
        @Size(min = 2, max = 40, message = "The surname must be between 2 and 40 characters!")
        String cognome,

        @NotEmpty(message = "The email is required!")
        @Email(message = "The entered email is not valid")
        String email,

        @NotEmpty(message = "The password is required!")
        @Size(min = 8, message = "The password must be at least 8 characters long!")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "The password must contain at least one uppercase letter, one number, and one special character.")
        String password,

        @NotEmpty(message = "The beauty center name is required!")
        @Size(min = 2, max = 40, message = "The beauty center name must be between 2 and 40 characters!")
        String nomeCentroEstetico,

        @NotEmpty(message = "The beauty center address is required!")
        @Size(min = 2, max = 40, message = "The address must be between 2 and 40 characters!")
        String indirizzo,

        @NotEmpty(message = "The beauty center city is required!")
        @Size(min = 2, max = 40, message = "The city must be between 2 and 40 characters!")
        String citta,

        @NotNull(message = "The beauty center treatment is required!")
        Trattamento trattamento

) {
}
