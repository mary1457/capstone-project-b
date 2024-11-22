package mariapiabaldoin.capstone_project_b.repositories;


import mariapiabaldoin.capstone_project_b.entities.Cliente;
import mariapiabaldoin.capstone_project_b.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {
    List<Prenotazione> findByCliente(Cliente cliente);

    Prenotazione findByIdAndCliente(UUID prenotazioneId, Cliente cliente);
}

