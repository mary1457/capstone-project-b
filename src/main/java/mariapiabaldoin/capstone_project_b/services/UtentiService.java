package mariapiabaldoin.capstone_project_b.services;

import mariapiabaldoin.capstone_project_b.entities.*;
import mariapiabaldoin.capstone_project_b.exceptions.BadRequestException;
import mariapiabaldoin.capstone_project_b.exceptions.NotFoundException;
import mariapiabaldoin.capstone_project_b.payloads.CentroEsteticoDTO;
import mariapiabaldoin.capstone_project_b.payloads.CentroEsteticoUpdateDTO;
import mariapiabaldoin.capstone_project_b.payloads.ClienteDTO;
import mariapiabaldoin.capstone_project_b.payloads.ClienteUpdateDTO;
import mariapiabaldoin.capstone_project_b.repositories.CentroEsteticoRepository;
import mariapiabaldoin.capstone_project_b.repositories.UtentiRepository;
import mariapiabaldoin.capstone_project_b.response.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UtentiService {

    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private CentroEsteticoRepository centroEsteticoRepository;


    @Autowired
    private PasswordEncoder bcrypt;

    public ResultResponse saveCliente(ClienteDTO body) {

        this.utentiRepository.findByEmail(body.email()).ifPresent(

                utente -> {
                    throw new BadRequestException("Email " + body.email() + " is already in use");
                }
        );


        String avatar = "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome();


        Utente newUtente = new Cliente(body.nome(), body.cognome(), body.email(), bcrypt.encode(body.password()));

        newUtente.setAvatar(avatar);

        this.utentiRepository.save(newUtente);

        return new ResultResponse("Registration successfully completed", true);
    }


    public ResultResponse saveCentroEstetico(CentroEsteticoDTO body) {

        this.utentiRepository.findByEmail(body.email()).ifPresent(

                utente -> {
                    throw new BadRequestException("Email " + body.email() + " is already in use");
                }
        );


        String avatar = "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome();


        Utente newUtente = new CentroEstetico(body.nome(), body.cognome(), body.email(), bcrypt.encode(body.password()), body.nomeCentroEstetico(), body.indirizzo(), body.citta(), body.trattamento());

        newUtente.setAvatar(avatar);

        this.utentiRepository.save(newUtente);

        return new ResultResponse("Registration successfully completed", true);
    }

    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return this.utentiRepository.findAll(pageable);
    }

    public Utente findById(UUID utenteId) {
        return this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }

    public Utente findByIdAndUpdate(UUID utenteId, ClienteUpdateDTO body) {

        Utente found = this.findById(utenteId);


        if (!found.getEmail().equals(body.email())) {
            this.utentiRepository.findByEmail(body.email()).ifPresent(

                    user -> {
                        throw new BadRequestException("Email " + body.email() + " is already in use");
                    }
            );
        }


        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setEmail(body.email());
        found.setAvatar("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());


        return this.utentiRepository.save(found);
    }


    public void findByIdAndDelete(UUID utenteId) {
        Utente found = this.findById(utenteId);
        this.utentiRepository.delete(found);
    }


    public Utente findByEmail(String email) {
        return this.utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("The user with email " + email + " was not found"));
    }


    public List<Cliente> searchClientiByCentroEstetico(UUID centroEsteticoId) {
        return utentiRepository.findClientiPrenotazioneInCentroEstetico(centroEsteticoId);
    }

    public List<CentroEstetico> searchByTrattamentoCittaAndData(
            Trattamento trattamento,
            String citta,
            LocalDateTime dataInizio,
            LocalDateTime dataFine) {

       
        List<CentroEstetico> centriEstetici = utentiRepository.findCentroEsteticoDisponibili(
                trattamento, citta, true);


        List<UUID> centroIds = centriEstetici.stream()
                .map(CentroEstetico::getId)
                .collect(Collectors.toList());

        List<Disponibilita> disponibilita = utentiRepository.findDisponibilitaForCentriEstetici(
                centroIds, dataInizio, dataFine);


        for (CentroEstetico centro : centriEstetici) {
            List<Disponibilita> disponibilitaCentri = disponibilita.stream()
                    .filter(d -> d.getCentroEstetico().getId().equals(centro.getId()))
                    .collect(Collectors.toList());

            centro.setDisponibilita(disponibilitaCentri);
        }

        return centriEstetici;
    }


    public CentroEsteticoUpdateDTO getCentroEsteticoUpadateDTOById(UUID id) {
        return centroEsteticoRepository.findCentroEsteticoUpadateDTOById(id);
    }


}
