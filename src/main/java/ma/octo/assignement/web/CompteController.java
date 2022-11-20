package ma.octo.assignement.web;


import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@EnableAutoConfiguration
@RequestMapping("/api/comptes")
public class CompteController {

    private final CompteService compteService;

    @Autowired
    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    @GetMapping("")
    List<Compte> loadAllCompte() {
        return compteService.findAll();
    }

}
