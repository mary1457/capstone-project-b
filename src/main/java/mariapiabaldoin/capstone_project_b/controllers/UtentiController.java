package mariapiabaldoin.capstone_project_b.controllers;


import mariapiabaldoin.capstone_project_b.entities.CentroEstetico;
import mariapiabaldoin.capstone_project_b.entities.Trattamento;
import mariapiabaldoin.capstone_project_b.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
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
}