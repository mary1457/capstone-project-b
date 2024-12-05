package mariapiabaldoin.capstone_project_b.repositories;

import mariapiabaldoin.capstone_project_b.entities.CentroEstetico;
import mariapiabaldoin.capstone_project_b.payloads.CentroEsteticoProfileDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CentroEsteticoRepository extends JpaRepository<CentroEstetico, UUID> {

    @Query("SELECT new mariapiabaldoin.capstone_project_b.payloads.CentroEsteticoProfileDTO( " +
            "ce.nome, ce.cognome, ce.email, " +
            "ce.nomeCentroEstetico, ce.indirizzo, ce.citta, ce.trattamento, ce.avatar) " +
            "FROM CentroEstetico ce WHERE ce.id = :id")
    CentroEsteticoProfileDTO findCentroEsteticoProfileDTOById(@Param("id") UUID id);

}
