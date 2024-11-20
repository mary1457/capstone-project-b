package mariapiabaldoin.capstone_project_b.services;


import mariapiabaldoin.capstone_project_b.entities.CentroEstetico;
import mariapiabaldoin.capstone_project_b.entities.Cliente;
import mariapiabaldoin.capstone_project_b.entities.Trattamento;
import mariapiabaldoin.capstone_project_b.entities.Utente;
import mariapiabaldoin.capstone_project_b.exceptions.BadRequestException;
import mariapiabaldoin.capstone_project_b.exceptions.NotFoundException;
import mariapiabaldoin.capstone_project_b.payloads.NewCentroEsteticoDTO;
import mariapiabaldoin.capstone_project_b.payloads.NewClienteDTO;
import mariapiabaldoin.capstone_project_b.payloads.ResultDTO;
import mariapiabaldoin.capstone_project_b.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UtentiService {

    @Autowired
    private UtentiRepository utentiRepository;


    @Autowired
    private PasswordEncoder bcrypt;

    public ResultDTO saveCliente(NewClienteDTO body) {

        this.utentiRepository.findByEmail(body.email()).ifPresent(

                utente -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );


        String avatar = "https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname();


        Utente newUtente = new Cliente(body.name(), body.surname(), body.email(), bcrypt.encode(body.password()));

        newUtente.setAvatar(avatar);

        this.utentiRepository.save(newUtente);

        return new ResultDTO("Registrazione", true);
    }


    public ResultDTO saveCentroEstetico(NewCentroEsteticoDTO body) {

        this.utentiRepository.findByEmail(body.email()).ifPresent(

                utente -> {
                    throw new BadRequestException("Email " + body.email() + " già in uso!");
                }
        );


        String avatar = "https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname();


        Utente newUtente = new CentroEstetico(body.name(), body.surname(), body.email(), bcrypt.encode(body.password()), body.nameBeautyCenter(), body.address(), body.city(), body.trattamenti());

        newUtente.setAvatar(avatar);

        this.utentiRepository.save(newUtente);

        return new ResultDTO("Registrazione", true);
    }

    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.utentiRepository.findAll(pageable);
    }

    public Utente findById(UUID utenteId) {
        return this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    public Utente findByIdAndUpdate(UUID utenteId, NewClienteDTO body) {

        Utente found = this.findById(utenteId);


        if (!found.getEmail().equals(body.email())) {
            this.utentiRepository.findByEmail(body.email()).ifPresent(

                    user -> {
                        throw new BadRequestException("Email " + body.email() + " già in uso!");
                    }
            );
        }


        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());
        found.setPassword(body.password());
        found.setAvatar("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());


        return this.utentiRepository.save(found);
    }


    public void findByIdAndDelete(UUID utenteId) {
        Utente found = this.findById(utenteId);
        this.utentiRepository.delete(found);
    }


    public Utente findByEmail(String email) {
        return this.utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato"));
    }


    public List<CentroEstetico> searchByTrattamentiAndCity(Trattamento trattamenti, String city) {
        return utentiRepository.findByTrattamentiAndCityAndAbilitato(trattamenti, city, true);
    }
}



