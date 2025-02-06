package org.example.investimentoapi.controller;

import org.example.investimentoapi.dto.FiiPrecoMedioResponse;
import org.example.investimentoapi.dto.TransacaoRequest;
import org.example.investimentoapi.dto.TransacaoResponse;
import org.example.investimentoapi.mapper.TransacaoMapper;
import org.example.investimentoapi.model.TransacaoFii;
import org.example.investimentoapi.service.TransacaoFiiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao/fiis")
public class TransacaoFiiController {

    @Autowired
    private TransacaoFiiService transacaoFiiService;

    @Autowired
    private TransacaoMapper transacaoMapper;

    @PostMapping
    public String registrarTransacao(@RequestBody TransacaoRequest transacaoRequest) throws Exception {
        return transacaoFiiService.registrarTransacao(transacaoRequest);
    }

    @GetMapping("/portfolio")
    public List<TransacaoResponse> getPortfolio() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TransacaoFii> transacoes = transacaoFiiService.getPortfolio(username);
        return transacaoMapper.toDtoFii(transacoes);
    }

    @GetMapping("/preco-medio")
    public List<FiiPrecoMedioResponse> getFiisComPrecoMedio() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return transacaoFiiService.getFiisComPrecoMedio(username);
    }
}
