package mariapiabaldoin.capstone_project_b.repositories;


import mariapiabaldoin.capstone_project_b.entities.Cliente;
import mariapiabaldoin.capstone_project_b.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {
    List<Prenotazione> findByCliente(Cliente cliente);

    Prenotazione findByIdAndCliente(UUID prenotazioneId, Cliente cliente);

    Prenotazione findByData(LocalDateTime data);

    @Query("SELECT p FROM Prenotazione p " +
            "WHERE p.centroEstetico.id = :centroEsteticoId " +
            "AND FUNCTION('DATE', p.data) = CURRENT_DATE " +
            "ORDER BY p.data ASC")
    List<Prenotazione> findPrenotazioniOggiByCentroEstetico(@Param("centroEsteticoId") UUID centroEsteticoId);


    @Query("SELECT p FROM Prenotazione p " +
            "WHERE p.centroEstetico.id = :centroEsteticoId " +
            "AND FUNCTION('MONTH', p.data) = FUNCTION('MONTH', CURRENT_DATE) " +
            "AND FUNCTION('YEAR', p.data) = FUNCTION('YEAR', CURRENT_DATE) " +
            "ORDER BY p.data ASC")
    List<Prenotazione> findPrenotazioniMeseCorrenteByCentroEstetico(@Param("centroEsteticoId") UUID centroEsteticoId);

}

