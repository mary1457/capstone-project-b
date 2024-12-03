package mariapiabaldoin.capstone_project_b.repositories;


import mariapiabaldoin.capstone_project_b.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UtentiRepository extends JpaRepository<Utente, UUID> {
    Optional<Utente> findByEmail(String email);


    @Query("SELECT DISTINCT c FROM Cliente c " +
            "JOIN Prenotazione p ON c.id = p.cliente.id " +
            "WHERE p.centroEstetico.id = :centroEsteticoId " +
            "ORDER BY c.email ASC")
    List<Cliente> findClientiPrenotazioneInCentroEstetico(@Param("centroEsteticoId") UUID centroEsteticoId);


    @Query("""
            SELECT ce FROM CentroEstetico ce
            LEFT JOIN FETCH ce.disponibilita d
            WHERE ce.citta = :citta
              AND ce.trattamento = :trattamento
              AND ce.abilitato = :abilitato
              AND (
                  d IS NULL OR (d.data BETWEEN :dataInizio AND :dataFine)
              )
            """)
    List<CentroEstetico> findDisponibiliByTrattamentoCittaAbilitatoAndData(
            @Param("trattamento") Trattamento trattamento,
            @Param("citta") String citta,
            @Param("abilitato") boolean abilitato,
            @Param("dataInizio") LocalDateTime dataInizio,
            @Param("dataFine") LocalDateTime dataFine
    );

    @Query("""
            SELECT ce FROM CentroEstetico ce
            WHERE ce.citta = :citta
              AND ce.trattamento = :trattamento
              AND ce.abilitato = :abilitato
            """)
    List<CentroEstetico> findCentroEsteticoDisponibili(
            @Param("trattamento") Trattamento trattamento,
            @Param("citta") String citta,
            @Param("abilitato") boolean abilitato
    );

    @Query("""
            SELECT d FROM Disponibilita d
            WHERE d.centroEstetico.id IN :centroEsteticiIds
              AND d.data BETWEEN :dataInizio AND :dataFine
            """)
    List<Disponibilita> findDisponibilitaForCentriEstetici(
            @Param("centroEsteticiIds") List<UUID> centroEsteticiIds,
            @Param("dataInizio") LocalDateTime dataInizio,
            @Param("dataFine") LocalDateTime dataFine
    );


}