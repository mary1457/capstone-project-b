package mariapiabaldoin.capstone_project_b.controllers;


import mariapiabaldoin.capstone_project_b.entities.Prenotazione;
import mariapiabaldoin.capstone_project_b.entities.Utente;
import mariapiabaldoin.capstone_project_b.exceptions.BadRequestException;
import mariapiabaldoin.capstone_project_b.payloads.PrenotazioneDTO;
import mariapiabaldoin.capstone_project_b.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/res")
public class PrenotazioniController {
    @Autowired
    private PrenotazioniService prenotazioniService;


    @GetMapping("/me")
    @ResponseBody
    public List<Prenotazione> get(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return this.prenotazioniService.findByIdCliente(currentAuthenticatedUtente.getId());


    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione save(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody @Validated PrenotazioneDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }

        return this.prenotazioniService.save(currentAuthenticatedUtente.getId(), body);
    }

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @PathVariable UUID prenotazioneId) {
        this.prenotazioniService.findByIdAndDelete(prenotazioneId, currentAuthenticatedUtente.getId());
    }

    @GetMapping("/today")
    @ResponseBody
    public List<Prenotazione> getResToday(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return this.prenotazioniService.getPrenotazioniOggi(currentAuthenticatedUtente.getId());


    }

    @GetMapping("/month")
    @ResponseBody
    public List<Prenotazione> getResMonth(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return this.prenotazioniService.getPrenotazioniMese(currentAuthenticatedUtente.getId());


    }
}