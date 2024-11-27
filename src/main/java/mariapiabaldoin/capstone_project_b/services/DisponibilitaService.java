package mariapiabaldoin.capstone_project_b.services;


import mariapiabaldoin.capstone_project_b.entities.CentroEstetico;
import mariapiabaldoin.capstone_project_b.entities.Disponibilita;
import mariapiabaldoin.capstone_project_b.entities.Prenotazione;
import mariapiabaldoin.capstone_project_b.entities.Stato;
import mariapiabaldoin.capstone_project_b.exceptions.NotFoundException;
import mariapiabaldoin.capstone_project_b.repositories.DisponibilitaRepository;
import mariapiabaldoin.capstone_project_b.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class DisponibilitaService {

    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private DisponibilitaRepository disponibilitaRepository;


    public List<Disponibilita> findByIdCEntroEsteticoAndStato(UUID centroEsteticoId) {

        CentroEstetico centroEstetico = (CentroEstetico) utentiRepository.findById(centroEsteticoId)
                .orElseThrow(() -> new NotFoundException(centroEsteticoId));
        return this.disponibilitaRepository.findByCentroEsteticoAndStato(centroEstetico, Stato.PRENOTATO);
    }

    public Disponibilita findByPrenotazione(Prenotazione prenotazione) {


        return this.disponibilitaRepository.findByPrenotazione(prenotazione);
    }


}
