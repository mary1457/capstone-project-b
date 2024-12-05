package mariapiabaldoin.capstone_project_b.repositories;


import mariapiabaldoin.capstone_project_b.entities.Cliente;
import mariapiabaldoin.capstone_project_b.entities.Preferito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface PreferitiRepository extends JpaRepository<Preferito, UUID> {
    List<Preferito> findByCliente(Cliente cliente);


    Preferito findByIdAndCliente(UUID preferitoId, Cliente cliente);

    List<Preferito> findByCentroEsteticoId(UUID centroEsteticoId);

    void deleteByCentroEsteticoId(UUID centroEsteticoId);
}

