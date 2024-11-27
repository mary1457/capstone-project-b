package mariapiabaldoin.capstone_project_b.repositories;


import mariapiabaldoin.capstone_project_b.entities.CentroEstetico;
import mariapiabaldoin.capstone_project_b.entities.Cliente;
import mariapiabaldoin.capstone_project_b.entities.Trattamento;
import mariapiabaldoin.capstone_project_b.entities.Utente;
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

    @Query("""
            SELECT DISTINCT ce
            FROM CentroEstetico ce
            LEFT JOIN FETCH ce.disponibilita d
            WHERE ce.trattamento = :trattamento
              AND ce.citta = :citta
              AND ce.abilitato = :abilitato
              AND (d.data BETWEEN :dataInizio AND :dataFine OR d IS NULL)
            """)
    List<CentroEstetico> findDisponibiliByTrattamentoCittaAbilitatoAndData(
            @Param("trattamento") Trattamento trattamento,
            @Param("citta") String citta,
            @Param("abilitato") boolean abilitato,
            @Param("dataInizio") LocalDateTime datainizio,
            @Param("dataFine") LocalDateTime dataFine
    );

    @Query("SELECT DISTINCT c FROM Cliente c " +
            "JOIN Prenotazione p ON c.id = p.cliente.id " +
            "WHERE p.centroEstetico.id = :centroEsteticoId " +
            "ORDER BY c.email ASC")
    List<Cliente> findClientiPrenotazioneInCentroEstetico(@Param("centroEsteticoId") UUID centroEsteticoId);


}

