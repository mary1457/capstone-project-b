package mariapiabaldoin.capstone_project_b.services;


import mariapiabaldoin.capstone_project_b.entities.CentroEstetico;
import mariapiabaldoin.capstone_project_b.entities.Cliente;
import mariapiabaldoin.capstone_project_b.entities.Prenotazione;
import mariapiabaldoin.capstone_project_b.exceptions.BadRequestException;
import mariapiabaldoin.capstone_project_b.exceptions.NotFoundException;
import mariapiabaldoin.capstone_project_b.exceptions.UnauthorizedException;
import mariapiabaldoin.capstone_project_b.payloads.PrenotazioneDTO;
import mariapiabaldoin.capstone_project_b.repositories.PrenotazioniRepository;
import mariapiabaldoin.capstone_project_b.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class PrenotazioniService {

    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private PrenotazioniRepository prenotazioniRepository;


    public Prenotazione save(UUID clienteId, PrenotazioneDTO body) {


        Cliente cliente = (Cliente) utentiRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException(clienteId));

        CentroEstetico centroEstetico = (CentroEstetico) utentiRepository.findById(body.centroEsteticoId())
                .orElseThrow(() -> new NotFoundException(body.centroEsteticoId()));


        if (LocalDateTime.now().isBefore(body.data())) {
            Prenotazione newPrenotazione = new Prenotazione(cliente, centroEstetico, body.data());
            return this.prenotazioniRepository.save(newPrenotazione);
        } else {
            throw new BadRequestException("La data " + body.data() + " non può essere antecedente ad oggi");
        }


    }


    public List<Prenotazione> findByIdCliente(UUID clienteId) {

        Cliente cliente = (Cliente) utentiRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException(clienteId));
        return this.prenotazioniRepository.findByCliente(cliente);
    }


    public Prenotazione findById(UUID prenotazioneId) {
        return this.prenotazioniRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }

    public Prenotazione findByIdAndIdCliente(UUID prenotazioneId, UUID clienteId) {
        Cliente cliente = (Cliente) utentiRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException(clienteId));
        Prenotazione prenotazione = prenotazioniRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NotFoundException(prenotazioneId));
        return this.prenotazioniRepository.findByIdAndCliente(prenotazioneId, cliente);
    }


    public void findByIdAndDelete(UUID prenotazioneId, UUID clienteId) {

        Prenotazione found = this.findByIdAndIdCliente(prenotazioneId, clienteId);
        if (found == null) {
            throw new UnauthorizedException("Azione non possibile");
        }
        this.prenotazioniRepository.delete(found);
    }


}
