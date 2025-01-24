package org.example.investimentoapi.controller;

import org.example.investimentoapi.model.Acao;
import org.example.investimentoapi.service.AcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acoes")
public class AcaoController {
    @Autowired
    private AcaoService acaoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String registrarAcao(@RequestBody Acao acao) throws Exception {
        return acaoService.registrarAcao(acao);
    }

    @GetMapping
    public List<Acao> getPortfolio() {
        return acaoService.getPortfolio();
    }
}
