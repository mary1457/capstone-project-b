package mariapiabaldoin.capstone_project_b.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteDTO(

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
        String password

) {
}
