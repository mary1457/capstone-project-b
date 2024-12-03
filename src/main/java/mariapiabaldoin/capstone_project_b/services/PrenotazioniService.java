package mariapiabaldoin.capstone_project_b.services;


import mariapiabaldoin.capstone_project_b.entities.*;
import mariapiabaldoin.capstone_project_b.exceptions.BadRequestException;
import mariapiabaldoin.capstone_project_b.exceptions.NotFoundException;
import mariapiabaldoin.capstone_project_b.exceptions.UnauthorizedException;
import mariapiabaldoin.capstone_project_b.payloads.PrenotazioneDTO;
import mariapiabaldoin.capstone_project_b.repositories.DisponibilitaRepository;
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

    @Autowired
    private DisponibilitaRepository disponibilitaRepository;


    public Prenotazione save(UUID clienteId, PrenotazioneDTO body) {

        Cliente cliente = (Cliente) utentiRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException(clienteId));


        CentroEstetico centroEstetico = (CentroEstetico) utentiRepository.findById(body.centroEsteticoId())
                .orElseThrow(() -> new NotFoundException(body.centroEsteticoId()));


        if (LocalDateTime.now().isBefore(body.data())) {

            this.findByData(body.data());


            Prenotazione newPrenotazione = new Prenotazione(cliente, centroEstetico, body.data());


            Prenotazione salvata = this.prenotazioniRepository.save(newPrenotazione);

            Disponibilita newDisponibilita = new Disponibilita(centroEstetico, body.data(), Stato.PRENOTATO, salvata);
            this.disponibilitaRepository.save(newDisponibilita);
            return salvata;


        } else {
            throw new BadRequestException("The date cannot be earlier than today");
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
            throw new UnauthorizedException("Action not possible");
        }


        Disponibilita disponibilita = disponibilitaRepository.findByPrenotazione(found);
        if (disponibilita != null) {

            disponibilitaRepository.delete(disponibilita);
        }


        prenotazioniRepository.delete(found);
    }


    public void findByData(LocalDateTime data) {

        Prenotazione found = this.prenotazioniRepository.findByData(data);
        if (found != null) {
            throw new UnauthorizedException("Action not possible");
        }
    }

    public List<Prenotazione> getPrenotazioniOggi(UUID centroEsteticoId) {
        return prenotazioniRepository.findPrenotazioniOggiByCentroEstetico(centroEsteticoId);
    }

    public List<Prenotazione> getPrenotazioniMese(UUID centroEsteticoId) {
        return prenotazioniRepository.findPrenotazioniMeseCorrenteByCentroEstetico(centroEsteticoId);
    }


}
