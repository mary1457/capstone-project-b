package mariapiabaldoin.capstone_project_b.controllers;


import mariapiabaldoin.capstone_project_b.entities.CentroEstetico;
import mariapiabaldoin.capstone_project_b.entities.Trattamento;
import mariapiabaldoin.capstone_project_b.entities.Utente;
import mariapiabaldoin.capstone_project_b.payloads.NewClienteDTO;
import mariapiabaldoin.capstone_project_b.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/utenti")
public class UtentiController {
    @Autowired
    private UtentiService utentiService;


    @GetMapping("/search")
    @ResponseBody
    public List<CentroEstetico> getSearch(@RequestParam(defaultValue = "test") Trattamento trattamenti, @RequestParam(required = false) String city) {
        System.out.println(trattamenti);
        System.out.println(city);
        if (city != null && trattamenti != null) {
            return utentiService.searchByTrattamentiAndCity(trattamenti, city);
        }
        throw new IllegalArgumentException("Tipo di ordinamento non valido");


    }

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return currentAuthenticatedUtente;
    }

    @PutMapping("/me")
    public Utente updateProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody @Validated NewClienteDTO body) {
        return this.utentiService.findByIdAndUpdate(currentAuthenticatedUtente.getId(), body);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        this.utentiService.findByIdAndDelete(currentAuthenticatedUtente.getId());
    }
}