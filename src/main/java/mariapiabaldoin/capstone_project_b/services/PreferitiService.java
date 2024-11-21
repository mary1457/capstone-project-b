package mariapiabaldoin.capstone_project_b.services;


import mariapiabaldoin.capstone_project_b.entities.Cliente;
import mariapiabaldoin.capstone_project_b.entities.Preferito;
import mariapiabaldoin.capstone_project_b.exceptions.NotFoundException;
import mariapiabaldoin.capstone_project_b.exceptions.UnauthorizedException;
import mariapiabaldoin.capstone_project_b.payloads.PreferitoDTO;
import mariapiabaldoin.capstone_project_b.repositories.PreferitiRepository;
import mariapiabaldoin.capstone_project_b.repositories.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PreferitiService {

    @Autowired
    private UtentiRepository utentiRepository;


    @Autowired
    private PreferitiRepository preferitiRepository;


    @Autowired
    private PasswordEncoder bcrypt;

    public Preferito savePreferito(UUID clienteId, PreferitoDTO body) {


        Cliente cliente = (Cliente) utentiRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException(clienteId));


        Preferito newPreferito = new Preferito(body.nameBeautyCenter(), body.address(), cliente);


        return this.preferitiRepository.save(newPreferito);
    }


    public List<Preferito> findByIdCliente(UUID clienteId) {

        Cliente cliente = (Cliente) utentiRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException(clienteId));
        return this.preferitiRepository.findByCliente(cliente);
    }

    public Preferito findById(UUID preferitoId) {
        return this.preferitiRepository.findById(preferitoId).orElseThrow(() -> new NotFoundException(preferitoId));
    }

    public Preferito findByIdAndIdCliente(UUID preferitoId, UUID clienteId) {
        Cliente cliente = (Cliente) utentiRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException(clienteId));
        Preferito preferito = preferitiRepository.findById(preferitoId)
                .orElseThrow(() -> new NotFoundException(preferitoId));
        return this.preferitiRepository.findByIdAndCliente(preferitoId, cliente);
    }


    public void findByIdAndDelete(UUID preferitoId, UUID clienteId) {

        Preferito found = this.findByIdAndIdCliente(preferitoId, clienteId);
        if (found == null) {
            throw new UnauthorizedException("Azione non possibile");
        }
        this.preferitiRepository.delete(found);
    }


}



