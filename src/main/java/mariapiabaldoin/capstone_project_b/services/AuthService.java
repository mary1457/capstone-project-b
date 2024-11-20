package mariapiabaldoin.capstone_project_b.services;


import mariapiabaldoin.capstone_project_b.entities.CentroEstetico;
import mariapiabaldoin.capstone_project_b.entities.Utente;
import mariapiabaldoin.capstone_project_b.exceptions.UnauthorizedException;
import mariapiabaldoin.capstone_project_b.payloads.UtenteLoginDTO;
import mariapiabaldoin.capstone_project_b.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    private UtentiService utentiService;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(UtenteLoginDTO body) {

        Utente found = this.utentiService.findByEmail(body.email());

        if (found instanceof CentroEstetico) {
            CentroEstetico centro = (CentroEstetico) found;
            if (!centro.isAbilitato()) {
                throw new UnauthorizedException("Centro non abilitato!");
            }
        }
        if (bcrypt.matches(body.password(), found.getPassword())) {

            String accessToken = jwt.createToken(found);

            return accessToken;
        } else {

            throw new UnauthorizedException("Credenziali errate!");
        }
    }

}
