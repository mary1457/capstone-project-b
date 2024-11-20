package mariapiabaldoin.capstone_project_b.payloads;

import jakarta.validation.constraints.*;
import mariapiabaldoin.capstone_project_b.entities.Trattamento;

public record NewCentroEsteticoDTO(

        @NotEmpty(message = "Il nome è obbligatorio!")
        @Size(min = 2, max = 40, message = "Il nome deve essere compreso tra 2 e 40 caratteri!")
        String name,

        @NotEmpty(message = "Il cognome è obbligatorio!")
        @Size(min = 2, max = 40, message = "Il cognome deve essere compreso tra 2 e 40 caratteri!")
        String surname,

        @NotEmpty(message = "L'email è obbligatoria!")
        @Email(message = "L'email inserita non è valida")
        String email,

        @NotEmpty(message = "La password è obbligatoria!")
        @Size(min = 8, message = "La password deve contenere almeno 8 caratteri!")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "La password deve contenere almeno una lettera maiuscola, un numero e un carattere speciale.")
        String password,

        @NotEmpty(message = "Il nome del centro estetico è obbligatorio!")
        @Size(min = 2, max = 40, message = "Il nome deve essere compreso tra 2 e 40 caratteri!")
        String nameBeautyCenter,

        @NotEmpty(message = "L'indirizzo del centro estetico è obbligatorio!")
        @Size(min = 2, max = 40, message = "L'indirizzo deve essere compreso tra 2 e 40 caratteri!")
        String address,

        @NotEmpty(message = "La città del centro estetico è obbligatoria!")
        @Size(min = 2, max = 40, message = "La città deve essere compresa tra 2 e 40 caratteri!")
        String city,

        @NotNull(message = "Il trattamento del centro estetico è obbligatoria!")
        Trattamento trattamenti

) {
}
