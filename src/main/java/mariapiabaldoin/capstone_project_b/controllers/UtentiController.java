package mariapiabaldoin.capstone_project_b.controllers;


import mariapiabaldoin.capstone_project_b.entities.CentroEstetico;
import mariapiabaldoin.capstone_project_b.entities.Cliente;
import mariapiabaldoin.capstone_project_b.entities.Trattamento;
import mariapiabaldoin.capstone_project_b.entities.Utente;
import mariapiabaldoin.capstone_project_b.payloads.CentroEsteticoDeleteDTO;
import mariapiabaldoin.capstone_project_b.payloads.CentroEsteticoProfileDTO;
import mariapiabaldoin.capstone_project_b.payloads.CentroEsteticoUpdateDTO;
import mariapiabaldoin.capstone_project_b.payloads.ClienteUpdateDTO;
import mariapiabaldoin.capstone_project_b.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UtentiController {
    @Autowired
    private UtentiService utentiService;


    @GetMapping("/search")
    @ResponseBody
    public List<CentroEstetico> getSearch(
            @RequestParam(required = false) Trattamento trattamento,
            @RequestParam(required = false) String citta,
            @RequestParam(required = false) String data) {
        LocalDate localDate = LocalDate.parse(data.trim());


        LocalDateTime dataInizio = localDate.atTime(LocalTime.of(9, 0));
        LocalDateTime dataFine = localDate.atTime(LocalTime.of(18, 0));
       

        if (trattamento != null && citta != null && data != null) {
            return utentiService.searchByTrattamentoCittaAndData(trattamento, citta, dataInizio, dataFine);
        }


        throw new IllegalArgumentException("The treatments, city, and date parameters are required for this search");
    }


    @GetMapping("/me")
    public Cliente get(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return (Cliente) currentAuthenticatedUtente;
    }


    @PutMapping("/me")
    public Utente update(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody @Validated ClienteUpdateDTO body) {
        return this.utentiService.findByIdAndUpdate(currentAuthenticatedUtente.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        this.utentiService.findByIdAndDelete(currentAuthenticatedUtente.getId());
    }

    @GetMapping("/clients")
    public List<Cliente> getClients(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return this.utentiService.searchClientiByCentroEstetico(currentAuthenticatedUtente.getId());
    }


    @GetMapping("/bc")
    public ResponseEntity<CentroEsteticoProfileDTO> getCentroEsteticoById(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        CentroEsteticoProfileDTO dto = utentiService.getCentroEsteticoProfileDTOById(currentAuthenticatedUtente.getId());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/bc")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CentroEstetico> updateCentroEsteticoById(
            @AuthenticationPrincipal Utente currentAuthenticatedUtente,
            @RequestBody @Validated CentroEsteticoUpdateDTO body) {
        CentroEstetico updatedDto = utentiService.findByIdAndUpdateBc(body.centroEsteticoId(), body);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/bc")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody @Validated CentroEsteticoDeleteDTO body) {
        this.utentiService.findByIdAndDeleteBc(body.centroEsteticoId());
    }
}