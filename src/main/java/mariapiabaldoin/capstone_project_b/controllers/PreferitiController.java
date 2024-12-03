package mariapiabaldoin.capstone_project_b.controllers;


import mariapiabaldoin.capstone_project_b.entities.Preferito;
import mariapiabaldoin.capstone_project_b.entities.Utente;
import mariapiabaldoin.capstone_project_b.exceptions.BadRequestException;
import mariapiabaldoin.capstone_project_b.payloads.PreferitoDTO;
import mariapiabaldoin.capstone_project_b.services.PreferitiService;
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
@RequestMapping("/fav")
public class PreferitiController {
    @Autowired
    private PreferitiService preferitiService;


    @GetMapping("/me")
    @ResponseBody
    public List<Preferito> get(@AuthenticationPrincipal Utente currentAuthenticatedUtente) {
        return this.preferitiService.findByIdCliente(currentAuthenticatedUtente.getId());


    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Preferito save(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @RequestBody @Validated PreferitoDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Invalid input:  " + message);
        }

        return this.preferitiService.save(currentAuthenticatedUtente.getId(), body);
    }

    @DeleteMapping("/{preferitoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@AuthenticationPrincipal Utente currentAuthenticatedUtente, @PathVariable UUID preferitoId) {
        this.preferitiService.findByIdAndDelete(preferitoId, currentAuthenticatedUtente.getId());
    }
}