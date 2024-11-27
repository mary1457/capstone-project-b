package mariapiabaldoin.capstone_project_b.services;

import mariapiabaldoin.capstone_project_b.entities.CentroEstetico;
import mariapiabaldoin.capstone_project_b.entities.Cliente;
import mariapiabaldoin.capstone_project_b.entities.Utente;
import mariapiabaldoin.capstone_project_b.exceptions.UnauthorizedException;
import mariapiabaldoin.capstone_project_b.payloads.UtenteLoginDTO;
import mariapiabaldoin.capstone_project_b.response.UtenteLoginResponse;
import mariapiabaldoin.capstone_project_b.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UtentiService utentiService;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    public UtenteLoginResponse checkCredentialsAndGenerateToken(UtenteLoginDTO body) {


        Optional<Utente> found = Optional.ofNullable(this.utentiService.findByEmail(body.email()));


        if (found.isEmpty()) {
            throw new UnauthorizedException("User not found");
        }

        Utente user = found.get();


        if (user instanceof Cliente) {
            Cliente cliente = (Cliente) user;
            if (bcrypt.matches(body.password(), cliente.getPassword())) {
                String accessToken = jwt.createToken(cliente);
                return new UtenteLoginResponse(accessToken, "cliente");
            }
        }


        if (user instanceof CentroEstetico) {
            CentroEstetico centroEstetico = (CentroEstetico) user;
            if (bcrypt.matches(body.password(), centroEstetico.getPassword())) {

                if (!centroEstetico.isAbilitato()) {
                    throw new UnauthorizedException("Beauty center not enabled");
                }
                String accessToken = jwt.createToken(centroEstetico);
                return new UtenteLoginResponse(accessToken, "centroEstetico");
            }
        }

        // Se la password non corrisponde
        throw new UnauthorizedException("Incorrect credentials");
    }
}
