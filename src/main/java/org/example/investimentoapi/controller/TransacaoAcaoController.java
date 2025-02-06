package org.example.investimentoapi.controller;

import org.example.investimentoapi.dto.AcaoPrecoMedioResponse;
import org.example.investimentoapi.dto.TransacaoRequest;
import org.example.investimentoapi.dto.TransacaoResponse;
import org.example.investimentoapi.mapper.TransacaoMapper;
import org.example.investimentoapi.model.Transacao;
import org.example.investimentoapi.service.TransacaoAcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao/acoes")
public class TransacaoAcaoController {

    @Autowired
    private TransacaoAcaoService transacaoAcaoService;

    @Autowired
    private TransacaoMapper transacaoMapper;

    @PostMapping
    public String registrarTransacao(@RequestBody TransacaoRequest transacaoRequest) throws Exception {
        return transacaoAcaoService.registrarTransacao(transacaoRequest);
    }

    @GetMapping("/portfolio")
    public List<TransacaoResponse> getPortfolio() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Transacao> transacoes = transacaoAcaoService.getPortfolio(username);
        return transacaoMapper.toDto(transacoes);
    }

    @GetMapping("/preco-medio")
    public List<AcaoPrecoMedioResponse> getAcoesComPrecoMedio() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return transacaoAcaoService.getAcoesComPrecoMedio(username);
    }
}
