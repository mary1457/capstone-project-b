package mariapiabaldoin.capstone_project_b.controllers;


import mariapiabaldoin.capstone_project_b.exceptions.BadRequestException;
import mariapiabaldoin.capstone_project_b.payloads.CentroEsteticoDTO;
import mariapiabaldoin.capstone_project_b.payloads.ClienteDTO;
import mariapiabaldoin.capstone_project_b.payloads.UtenteLoginDTO;
import mariapiabaldoin.capstone_project_b.response.ResultResponse;
import mariapiabaldoin.capstone_project_b.response.UtenteLoginResponse;
import mariapiabaldoin.capstone_project_b.services.AuthService;
import mariapiabaldoin.capstone_project_b.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UtentiService utentiService;

    @PostMapping("/login")
    public UtenteLoginResponse login(@RequestBody UtenteLoginDTO body) {
        
        return this.authService.checkCredentialsAndGenerateToken(body);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultResponse save(@RequestBody @Validated ClienteDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("There are errors in the payload " + message);
        }

        return this.utentiService.saveCliente(body);
    }

    @PostMapping("/registerBeautyCenter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultResponse save(@RequestBody @Validated CentroEsteticoDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("There are errors in the payload " + message);
        }

        return this.utentiService.saveCentroEstetico(body);
    }
}