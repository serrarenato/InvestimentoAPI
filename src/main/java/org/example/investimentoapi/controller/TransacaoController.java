package org.example.investimentoapi.controller;


import org.example.investimentoapi.model.Transacao;
import org.example.investimentoapi.model.Acao;
import org.example.investimentoapi.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public String registrarTransacao(@RequestBody Transacao transacao) throws Exception {
        return transacaoService.registrarTransacao(transacao);
    }

    @GetMapping("/portfolio")
    public List<Acao> getPortfolio() {
        return transacaoService.getPortfolio();
    }
}