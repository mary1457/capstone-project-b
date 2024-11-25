package mariapiabaldoin.capstone_project_b.repositories;


import mariapiabaldoin.capstone_project_b.entities.CentroEstetico;
import mariapiabaldoin.capstone_project_b.entities.Disponibilita;
import mariapiabaldoin.capstone_project_b.entities.Prenotazione;
import mariapiabaldoin.capstone_project_b.entities.Stato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface DisponibilitaRepository extends JpaRepository<Disponibilita, UUID> {
    List<Disponibilita> findByCentroEsteticoAndStato(CentroEstetico centroEstetico, Stato stato);

    Disponibilita findByPrenotazione(Prenotazione prenotazione);
}

